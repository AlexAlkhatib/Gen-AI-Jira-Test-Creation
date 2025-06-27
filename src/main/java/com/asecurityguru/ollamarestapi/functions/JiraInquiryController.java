package com.asecurityguru.ollamarestapi.functions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

/**
 * Contrôleur REST pour gérer les requêtes d'enquête Jira.
 * Permet de récupérer les détails d'une story Jira et de générer des cas de test via un modèle AI.
 */
@RestController
public class JiraInquiryController {
    private static final Logger log = LoggerFactory.getLogger(JiraInquiryController.class);
    private final ChatClient chatClient;
    private final Function<JiraDataService.Request, JiraDataService.Response> jiraFunction;

    /**
     * Constructeur pour injecter le client ChatClient et la fonction Jira.
     *
     * @param chatClientBuilder Constructeur pour créer une instance de ChatClient.
     * @param jiraFunction      Fonction pour récupérer les détails d'une story Jira.
     */
    public JiraInquiryController(ChatClient.Builder chatClientBuilder,
                                 Function<JiraDataService.Request, JiraDataService.Response> jiraFunction) {
        this.chatClient = chatClientBuilder
                         .defaultFunctions("jiraFunction")
                         .build();
        this.jiraFunction = jiraFunction;
    }

    /**
     * Point d'accès HTTP GET pour récupérer les détails d'une story Jira et générer des cas de test.
     *
     * @param storyKey La clé unique de la story Jira à interroger.
     * @return Une chaîne de caractères contenant les cas de test générés ou un message d'erreur.
     */
    @GetMapping("/api/v1/jira-story")
    public String getJiraStoryDetails(@RequestParam String storyKey) {

        try {
            // Récupérer les détails de la story Jira
            JiraDataService.Response jiraResponse = jiraFunction.apply(new JiraDataService.Request(storyKey));

            // Générer des cas de test en utilisant le modèle AI basé sur les critères d'acceptation
            String prompt = "Based on the following acceptance criteria, generate detailed test cases with Test Case ID, Steps, Test Data, Test Case Description, and Expected Result:\n\n" 
                            + jiraResponse.acceptanceCriteria();

            String aiResponse = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

            log.info("Réponse de l'IA: {}", aiResponse);
            return aiResponse;
        } catch (Exception e) {
            log.error("Erreur lors du traitement de la requête pour la clé storyKey: {}", storyKey, e);
            return "Impossible de traiter votre demande pour le moment.";
        }
    }
}
