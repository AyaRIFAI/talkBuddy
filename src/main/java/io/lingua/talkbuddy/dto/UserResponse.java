package io.lingua.talkbuddy.dto;

public record UserResponse(Long id, String name, String email, String languageToLearn, String nativeLanguage) {
}