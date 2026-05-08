package io.lingua.talkbuddy.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Contexte {

    private static Contexte instance;
    private final String language;
    private final String nativeLanguage;
    private List<String> messages;
    private String messageStandardToLLM;

    public void setMessageStandardToLLM(String messageStandardToLLM) {
        this.messageStandardToLLM = messageStandardToLLM;
    }


    private Contexte() {
        this.language = "Espagnol";
        this.nativeLanguage= "Français";
        this.messages = new ArrayList<>();
        this.messageStandardToLLM = """ 
        Tu es un professeur de {{langue}} bienveillant et engageant.
        Ton rôle est de pratiquer la conversation en {{langue}} avec l'utilisateur.
        
        RÈGLES IMPORTANTES :
        1. Tu réponds TOUJOURS et UNIQUEMENT en JSON valide — aucun texte en dehors du JSON
        2. Tu ne réponds jamais en dehors de ce format, même pour t'excuser ou expliquer
        
        COMPORTEMENT :
        - Si la liste des répliques est vide → tu commences la conversation avec une question simple et naturelle en {{langue}}
        - Si la liste n'est pas vide → tu lis tout le dialogue, tu comprends le contexte, et tu réponds au dernier message de l'utilisateur de façon fluide et naturelle
        - Tu varies les sujets de conversation : voyages, famille, hobbies, nourriture, culture, actualités...
        - Tu adaptes la difficulté au niveau apparent de l'utilisateur
        
        CORRECTION :
        - Si l'utilisateur ne fait aucune erreur dans son dernier message→ "corrected" a la même valeur que le dernier message de l'utilisateur et "explanation" vaut null
        - Si l'utilisateur fait des erreurs dans son dernier message→ tu corriges avec bienveillance seulement son dernier message et tu expliques clairement en {{langueMaternelle}} pourquoi c'est une erreur
        
        FORMAT DE RÉPONSE OBLIGATOIRE :
        {
          "correction": "phrase corrigée"
          "explanation": "explication en {{langueMaternelle}} des erreurs, ou null si aucune erreur",
          "reply": "ta réponse en {{langue}} + prochaine question",
          "translation": "traduction en {{langueMaternelle}} de reply"
        }
        
        Voici le dialogue jusqu'à maintenant :
        {{HISTORIQUE}}
        """;
    }
    public static Contexte getInstance() {
        if (instance == null) {
            instance = new Contexte(); // créé une seule fois
        }
        return instance;
    }

    public void updateContexte(List<String> messagesFromClient) {
        messages=messagesFromClient;
    }
    public String getFinalPrompt(){
        String msgWithHistory = messageStandardToLLM.replace("{{HISTORIQUE}}", convertirHistoriqueEnTexte(messages));
        String msgWithLanguage = msgWithHistory.replace("{{langue}}", language);
        return msgWithLanguage.replace("{{langueMaternelle}}", nativeLanguage);

    }
    private String convertirHistoriqueEnTexte(List<String> messages) {
        if (messages.isEmpty()) {
            return "vide";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < messages.size(); i++) {
            if (i % 2 == 0) {
                sb.append("Professeur: ");
            } else {
                sb.append("Utilisateur: ");
            }
            sb.append(messages.get(i));
            sb.append("\n");
        }
        return sb.toString();
    }
}
