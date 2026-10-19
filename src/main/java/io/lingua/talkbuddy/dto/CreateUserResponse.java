package io.lingua.talkbuddy.dto;

public record CreateUserResponse(Long id, String name, String email, String languageToLearn, String nativeLanguage) {
}