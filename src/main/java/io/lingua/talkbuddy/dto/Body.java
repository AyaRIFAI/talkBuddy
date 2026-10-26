package io.lingua.talkbuddy.dto;

import java.util.List;

public record Body(String model, List<Message> messages) {}
