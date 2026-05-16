package io.lingua.talkbuddy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String mail;
    private String languageToLearn;
    private String nativeLanguage;
    public UserEntity() {
    }

    public UserEntity(Long userId, String name, String mail, String languageToLearn, String nativeLanguage) {
        this.userId = userId;
        this.name = name;
        this.mail = mail;
        this.languageToLearn = languageToLearn;
        this.nativeLanguage = nativeLanguage;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLanguageToLearn() {
        return languageToLearn;
    }

    public void setLanguageToLearn(String languageToLearn) {
        this.languageToLearn = languageToLearn;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }
}
