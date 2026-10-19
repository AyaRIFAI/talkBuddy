package io.lingua.talkbuddy.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.lingua.talkbuddy.client.AskGroq;
import io.lingua.talkbuddy.client.AskMistral;
import io.lingua.talkbuddy.entity.ConversationEntity;
import io.lingua.talkbuddy.entity.MessageEntity;
import io.lingua.talkbuddy.entity.UserEntity;
import io.lingua.talkbuddy.dto.MessageFromClient;
import io.lingua.talkbuddy.dto.MessageToClient;
import io.lingua.talkbuddy.repository.ConversationRepository;
import io.lingua.talkbuddy.repository.MessageRepository;
import io.lingua.talkbuddy.service.ConversationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@SpringBootTest
public class ConversationServiceIT {
    @MockitoBean
    private ConversationRepository conversationRepository;

    @MockitoBean
    private AskGroq askGroq;

    @MockitoBean
    private AskMistral askMistral;

    @MockitoBean
    private MessageRepository messageRepository;


    @Autowired
    private ConversationService conversationService;
    @Test
    void answerClient_utilisateurValide_GroqKO() throws JsonProcessingException {
        UserEntity userEntityFound = new UserEntity("raya", "raya@gmail.com", "Espagnol", "Français");

        ConversationEntity conversationEntityFound = new ConversationEntity(LocalDateTime.now(), userEntityFound);

        when(conversationRepository.findById(any())).thenReturn(Optional.of(conversationEntityFound));
        List<MessageEntity> messagesEntities = new ArrayList<>();
        MessageEntity messageFromTeacher = new MessageEntity("Professeur", "Hola, como estas hoy?", conversationEntityFound, LocalDateTime.now());
        MessageEntity messageFromUser = new MessageEntity("Utilisateur", "Hola, estoy bien, gracias y tu?", conversationEntityFound, LocalDateTime.now());
        MessageEntity messageFromTeacher2 = new MessageEntity("Professeur", "muy bien, gracias, que quieres hacer?", conversationEntityFound, LocalDateTime.now());

        messagesEntities.add(messageFromTeacher);
        messagesEntities.add(messageFromUser);
        messagesEntities.add(messageFromTeacher2);

        when(messageRepository.findAllByConversationOrderByDateAndTime(any())).thenReturn(messagesEntities);
        // when(messageRepository.save(messageFromTeacher)).thenReturn(messageFromTeacher);
        //when(messageRepository.save(messageFromUser)).thenReturn(messageFromUser);
        MessageFromClient messageFromClient =new MessageFromClient("quiero caminar en la manana!", 1L);
        MessageToClient fakeLLMResponseGroq = new MessageToClient("¡Muy bien! ¿Y qué más quieres hacer?", "Très bien ! Et quoi d'autre ?", "correction", "explanation", 1L, "Groq");
        MessageToClient fakeLLMResponseMistral = new MessageToClient("¡Muy bien! ¿Y qué más quieres hacer?", "Très bien ! Et quoi d'autre ?", "correction", "explanation", 1L, "Mistral");

        when(askGroq.askReply(any())).thenThrow(new RuntimeException());

        when(askMistral.askReply(any())).thenReturn(fakeLLMResponseMistral);
        MessageToClient messageToClient = conversationService.answerClient(messageFromClient);
        assertNotNull(messageToClient);
        assertEquals(1L,messageToClient.getConversationId() );
        assertEquals("¡Muy bien! ¿Y qué más quieres hacer?", messageToClient.getAnswerofLLM());
        assertEquals("Très bien ! Et quoi d'autre ?", messageToClient.getTranslationOfLLMAnswer());
        assertEquals("correction",messageToClient.getCorrectionForUser());
        assertEquals("explanation", messageToClient.getExplanationForUser());
        assertEquals("Mistral", messageToClient.getProvider());
    }
}
