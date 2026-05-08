package io.lingua.talkbuddy.model;

import org.springframework.stereotype.Component;


public class MessageToClient {
    private String answerofLLM;
    private String translationOfLLMAnswer;
    private String correctionForUser;
    private String explanationForUser;
    private String provider;

    public MessageToClient() {

    }

    public MessageToClient(String answerofLLM, String translationOfLLMAnswer, String correctionForUser,String explanationForUser, String provider) {
        this.answerofLLM = answerofLLM;
        this.translationOfLLMAnswer = translationOfLLMAnswer;
        this.correctionForUser = correctionForUser;
        this.explanationForUser = explanationForUser;
        this.provider = provider;
    }

    public String getAnswerofLLM() {
        return answerofLLM;
    }

    public void setAnswerofLLM(String answerofLLM) {
        this.answerofLLM = answerofLLM;
    }

    public String getTranslationOfLLMAnswer() {
        return translationOfLLMAnswer;
    }

    public void setTranslationOfLLMAnswer(String translationOfLLMAnswer) {
        this.translationOfLLMAnswer = translationOfLLMAnswer;
    }

    public String getCorrectionForUser() {
        return correctionForUser;
    }

    public void setCorrectionForUser(String correctionForUser) {
        this.correctionForUser = correctionForUser;
    }
    public String getExplanationForUser() {
        return explanationForUser;
    }
    public void setExplanationForUser(String explanationForUser) {
        this.explanationForUser = explanationForUser;
    }
    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }


}