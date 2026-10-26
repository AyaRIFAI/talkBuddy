package io.lingua.talkbuddy.dto;

import io.lingua.talkbuddy.model.Sender;

public record MessageOfConversation(Sender sender, String texte){

    @Override
    public String toString() {
        return sender + " : " + texte;
    }
}
