package io.lingua.talkbuddy.controller;

import io.lingua.talkbuddy.service.ConversationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebMvcTest(ConversationController.class) // ← charge JUSTE le controller
class ConversationControllerTest {

    @Autowired
    MockMvc mockMvc; // ← simule les requêtes HTTP

    @MockitoBean
    ConversationService conversationService; // ← faux service

    @Test
    void chatWithBuddy_retourne200() throws Exception {
        // ARRANGE — configure le faux service

        WebApplicationContext wac;

        MockMvc mockMvc = webAppContextSetup(wac).build();

        mockMvc.perform(post("/api/calcul-taxe")
                        .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                        .content("{['hola']}")) // Tu envoies la donnée
                .andExpect(status().isOk()) ;     // Le serveur répond 200 OK

        // ACT — simule la requête

        // ASSERT — vérifie la réponse
    }
}
