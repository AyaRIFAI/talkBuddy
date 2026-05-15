package io.lingua.talkbuddy.model;

public class Replique {
    String auteur;
    String texte;

    public Replique(String auteur, String texte) {
        this.auteur = auteur;
        this.texte = texte;
    }

    @Override
    public String toString() {
        return auteur + " : " + texte;
    }
}
