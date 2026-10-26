package io.lingua.talkbuddy.model;

import io.lingua.talkbuddy.dto.MessageOfConversation;
import io.lingua.talkbuddy.dto.Message;

import java.util.ArrayList;
import java.util.List;

public class Contexte {

    private static Contexte instance;
    private String language;
    private String nativeLanguage;
    private final List<MessageOfConversation> messageOfConversations;
    private String messageSystemToLLM;
    private String messageUserToLLM;
    private Message message;
    private Long conversationId;

    public Contexte() {
        System.out.println("initillizing");
        this.messageOfConversations = new ArrayList<>();
        this.messageSystemToLLM = """ 
                Tu es un professeur de {{langue}} bienveillant et engageant.
                Ton rôle est de pratiquer la conversation en {{langue}} avec l'utilisateur.
                
                RÈGLES IMPORTANTES :
                1. Tu réponds TOUJOURS et UNIQUEMENT en JSON valide — aucun texte en dehors du JSON
                2. Tu ne réponds jamais en dehors de ce format, même pour t'excuser ou expliquer
                
                COMPORTEMENT :
                - Si la liste des répliques est vide → tu commences la conversation avec une question simple et naturelle en {{langue}}
                - Si la liste n'est pas vide → tu lis tout le dialogue, tu comprends le contexte, et tu réponds au dernier messageOfConversation de l'utilisateur de façon fluide et naturelle
                - Tu varies les sujets de conversation : voyages, famille, hobbies, nourriture, culture, actualités...
                - Tu adaptes la difficulté au niveau apparent de l'utilisateur
                
                CORRECTION :
                - Si l'utilisateur ne fait aucune erreur dans son dernier messageOfConversation→ "corrected" a la même valeur que le dernier messageOfConversation de l'utilisateur et "explanation" vaut null
                - Si l'utilisateur fait des erreurs dans  son dernier messageOfConversation→ tu corriges avec bienveillance seulement son dernier messageOfConversation et tu expliques clairement en {{langueMaternelle}} pourquoi c'est une erreur
                
                FORMAT DE RÉPONSE OBLIGATOIRE :
                {
                  "correction": "phrase corrigée"
                  "explanation": "explication en {{langueMaternelle}} des erreurs, ou null si aucune erreur",
                  "reply": "ta réponse en {{langue}} + prochaine question",
                  "translation": "traduction en {{langueMaternelle}} de reply"
                }
                
                """;
        this.messageUserToLLM = """
                
                Voici le dialogue jusqu'à maintenant :
                {{HISTORIQUE}}
                """;
        System.out.println(messageSystemToLLM);
        Message message =new Message("system", messageSystemToLLM);
        Message message2 =new Message("system", messageSystemToLLM);


    }

    public void setMessageSystemToLLM(String messageSystemToLLM) {
        this.messageSystemToLLM = messageSystemToLLM;
    }

    public void updateContexteMessages(MessageOfConversation messageOfConversation) {
        messageOfConversations.add(messageOfConversation);
        System.out.println("--------------------------------------------------");
        System.out.println(messageOfConversations);
    }

    public void updateContexteLanguages(String language, String nativeLanguage) {
        this.language = language;
        this.nativeLanguage = nativeLanguage;
    }

    public List<Message> getFinalPrompt() {
        ArrayList<Message> instructions = new ArrayList<>();
        String messageUserToLLMReady = messageUserToLLM.replace("{{HISTORIQUE}}", messageOfConversations.toString());
        String msgWithLanguage = messageSystemToLLM.replace("{{langue}}", language);
        String messageSystemToLLMReady = msgWithLanguage.replace("{{langueMaternelle}}", nativeLanguage);
        instructions.add(new Message("system", messageSystemToLLMReady));
        instructions.add(new Message("user", messageUserToLLMReady));
        return instructions;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }
    /*private String convertirHistoriqueEnTexte(List<String> messages) {
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
    }*/
}
