package io.lingua.talkbuddy.service;

import io.lingua.talkbuddy.dto.*;
import io.lingua.talkbuddy.entity.ConversationEntity;
import io.lingua.talkbuddy.entity.MessageEntity;
import io.lingua.talkbuddy.entity.UserEntity;
import io.lingua.talkbuddy.mapper.ConversationMapper;
import io.lingua.talkbuddy.mapper.MessageMapper;
import io.lingua.talkbuddy.model.Contexte;
import io.lingua.talkbuddy.model.Sender;
import io.lingua.talkbuddy.repository.ConversationRepository;
import io.lingua.talkbuddy.repository.MessageRepository;
import io.lingua.talkbuddy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConversationService {
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final LLMCallerService llmCallerService;
    private final ConversationMapper conversationMapper;
    private final MessageMapper messageMapper;

    public ConversationService(UserRepository userRepository, ConversationRepository conversationRepository, MessageRepository messageRepository, LLMCallerService llmCallerService, ConversationMapper conversationMapper,MessageMapper messageMapper) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.llmCallerService = llmCallerService;
        this.conversationMapper=conversationMapper;
        this.messageMapper=messageMapper;
    }

    public MessageResponse createConversation(CreateConversationRequest createConversationRequest) {
        UserEntity user = userRepository.findById(createConversationRequest.userId()).orElseThrow(() -> new RuntimeException("User non trouvé"));
        ConversationEntity conversation =conversationMapper.toEntity(createConversationRequest);
        conversation.setUser(user);
        conversation.setCreatedAt(LocalDateTime.now());

        ConversationEntity conversationEntity = conversationRepository.save(conversation);
        ConversationResponse conversationResponse = conversationMapper.toResponse(conversationEntity);
        return answerClient(conversationResponse.id(),new SendMessageRequest(""));
    }

    public MessageResponse answerClient(Long conversationId, SendMessageRequest sendMessageRequest) {

        Contexte contexte = new Contexte();
        ConversationEntity conversation = conversationRepository.findById(conversationId).orElseThrow(() -> new RuntimeException("conversation non trouvée"));
        List<MessageEntity> messagesEntities = messageRepository.findAllByConversationOrderByCreatedAtAsc(conversation);
        UserEntity userEntity = conversation.getUser();
        System.out.println("userEntity : ");
        System.out.println(userEntity);
        contexte.updateContexteLanguages(userEntity.getLanguageToLearn(), userEntity.getNativeLanguage());
        contexte.setConversationId(conversationId);
        for (MessageEntity messageEntity : messagesEntities) {
            contexte.updateContexteMessages(new MessageOfConversation(messageEntity.getSender(), messageEntity.getContent()));
        }
        contexte.updateContexteMessages(new MessageOfConversation(Sender.USER, sendMessageRequest.content()));
        //messageMapper.toEntity(messageRequest);
        messageRepository.save(new MessageEntity(Sender.USER, sendMessageRequest.content(), null, null, null, conversation, LocalDateTime.now()));
        AnswerofLLM answerofLLM = llmCallerService.callGroq(contexte);
        MessageEntity messageEntityFromBuddy = new MessageEntity(Sender.BUDDY, answerofLLM.reply(), answerofLLM.correction(), answerofLLM.explanation(), answerofLLM.translation(), conversation, LocalDateTime.now());
        MessageEntity messageEntityFromBuddySaved = messageRepository.save(messageEntityFromBuddy);
        return messageMapper.toResponse(messageEntityFromBuddySaved);
    }


}