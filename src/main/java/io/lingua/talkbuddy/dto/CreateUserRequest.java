package io.lingua.talkbuddy.dto;

public record CreateUserRequest(String name, String email, String languageToLearn, String nativeLanguage) {
}