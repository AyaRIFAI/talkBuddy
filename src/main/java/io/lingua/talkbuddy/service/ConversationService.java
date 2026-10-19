package io.lingua.talkbuddy.service;

import io.lingua.talkbuddy.client.AskGroq;
import io.lingua.talkbuddy.client.AskMistral;
import io.lingua.talkbuddy.entity.ConversationEntity;
import io.lingua.talkbuddy.entity.MessageEntity;
import io.lingua.talkbuddy.entity.UserEntity;
import io.lingua.talkbuddy.model.Contexte;
import io.lingua.talkbuddy.dto.MessageFromClient;
import io.lingua.talkbuddy.dto.MessageToClient;
import io.lingua.talkbuddy.model.Replique;
import io.lingua.talkbuddy.repository.ConversationRepository;
import io.lingua.talkbuddy.repository.MessageRepository;
import io.lingua.talkbuddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConversationService {
    @Autowired
    private AskMistral askMistral;

    @Autowired
    private AskGroq askGroq;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private LLMCallerService llmCallerService;

    public MessageToClient createConversation(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User non trouvé"));

        ConversationEntity conversation = new ConversationEntity();
        conversation.setUser(user);
        conversation.setStartDayTime(LocalDateTime.now());

         ConversationEntity conversationEntity = conversationRepository.save(conversation);
        return answerClient(new MessageFromClient(conversationEntity.getConversationId()));
    }
    public MessageToClient   answerClient(MessageFromClient messageFromClient) {

        Contexte contexte= new Contexte();

        ConversationEntity conversation = conversationRepository.findById(messageFromClient.getConversationId())
                .orElseThrow(() -> new RuntimeException("conversation non trouvée"));
        UserEntity userEntity = conversation.getUser();
        System.out.println("userEntity : ");
        System.out.println(userEntity);
        contexte.updateContexteLanguages(userEntity.getLanguageToLearn(), userEntity.getNativeLanguage());
        contexte.setConversationId(messageFromClient.getConversationId());
        List<MessageEntity> messagesEntities = messageRepository.findAllByConversationOrderByDateAndTime(conversation);
        for (MessageEntity messageEntity:messagesEntities) {
            contexte.updateContexteMessages(new Replique(messageEntity.getAuthor(), messageEntity.getContent()));
        }
        contexte.updateContexteMessages(new Replique("Utilisateur", messageFromClient.getMessage()));
        messageRepository.save(new MessageEntity("Utilisateur", messageFromClient.getMessage(), conversation, LocalDateTime.now()));
        MessageToClient msgToClient =  llmCallerService.callGroq(messageFromClient, contexte);
        messageRepository.save(new MessageEntity("Professeur", msgToClient.getAnswerofLLM(), conversation, LocalDateTime.now()));
        contexte.updateContexteMessages(new Replique("Professeur", msgToClient.getAnswerofLLM()));
        return msgToClient;
    }


}