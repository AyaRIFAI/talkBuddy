package io.lingua.talkbuddy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.lingua.talkbuddy.client.AskGroq;
import io.lingua.talkbuddy.client.AskMistral;
import io.lingua.talkbuddy.entity.ConversationEntity;
import io.lingua.talkbuddy.entity.MessageEntity;
import io.lingua.talkbuddy.entity.UserEntity;
import io.lingua.talkbuddy.model.Contexte;
import io.lingua.talkbuddy.model.MessageFromClient;
import io.lingua.talkbuddy.model.MessageToClient;
import io.lingua.talkbuddy.model.User;
import io.lingua.talkbuddy.repository.ConversationRepository;
import io.lingua.talkbuddy.repository.MessageRepository;
import io.lingua.talkbuddy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConversationServiceTest {
    @Mock
    private ConversationRepository conversationRepository;  // faux repository
    @Mock
    private UserRepository userRepository;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private AskGroq askGroq;
    @Mock
    private AskMistral askMistral;
    @InjectMocks
    private ConversationService conversationService;  // vrai service avec le faux repo injecté

    /*void createConversation_utilisateurValide_retourneConversationAvecId() {
        // ARRANGE
        UserEntity userEntityFound = new UserEntity("raya", "raya@gmail.com", "Espagnol", "Français");
        userEntityFound.setUserId(1L);

        when(userRepository.findById(any())).thenReturn(Optional.of(userEntityFound));

        ConversationEntity conversationEntitySaved = new ConversationEntity(LocalDateTime.now(), userEntityFound);
        when(conversationRepository.save(any())).thenReturn(conversationEntitySaved);

        // ACT
        MessageToClient messageToClient = conversationService.createConversation(1L);

        // ASSERT

        assertNotNull(messageToClient);
        assertNotNull(messageToClient.getConversationId());
        assertNotNull(messageToClient.getAnswerofLLM());
        assertNotNull(messageToClient.getTranslationOfLLMAnswer());
        assertNull(messageToClient.getCorrectionForUser());
        assertNull(messageToClient.getExplanationForUser());
        assertEquals(messageToClient.getProvider(), "Groq");

    }*/
    @Test
    void answerClient_utilisateurValide_retourneConversationAvecId() throws JsonProcessingException {
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
        when(askGroq.askReply(any())).thenReturn(fakeLLMResponseGroq);
        MessageToClient messageToClient = conversationService.answerClient(messageFromClient);
        assertNotNull(messageToClient);
        assertEquals(1L,messageToClient.getConversationId() );
        assertEquals("¡Muy bien! ¿Y qué más quieres hacer?", messageToClient.getAnswerofLLM());
        assertEquals("Très bien ! Et quoi d'autre ?", messageToClient.getTranslationOfLLMAnswer());
        assertEquals("correction",messageToClient.getCorrectionForUser());
        assertEquals("explanation", messageToClient.getExplanationForUser());
        assertEquals("Groq", messageToClient.getProvider());
    }
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
        assertEquals("Groq", messageToClient.getProvider());
    }
}
