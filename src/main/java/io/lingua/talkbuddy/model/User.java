package io.lingua.talkbuddy.model;

public class User {
    private Long userId;
    private String name;
    private String mail;
    private String language;
    private String nativeLanguage;

    public User() {
    }

    public User(Long userId, String name, String mail, String language, String nativeLanguage) {
        this.userId = userId;
        this.name = name;
        this.mail = mail;
        this.language = language;
        this.nativeLanguage = nativeLanguage;
    }

    public User(String name, String mail, String language, String nativeLanguage) {
        this.name = name;
        this.mail = mail;
        this.language = language;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getNativeLanguage() {
        return nativeLanguage;
    }

    public void setNativeLanguage(String nativeLanguage) {
        this.nativeLanguage = nativeLanguage;
    }
}
