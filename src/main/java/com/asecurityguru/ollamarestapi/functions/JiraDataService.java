package com.asecurityguru.ollamarestapi.functions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.function.Function;

@Validated
public class JiraDataService implements Function<JiraDataService.Request, JiraDataService.Response> {

    private static final Logger log = LoggerFactory.getLogger(JiraDataService.class);
    private final JiraApiProperties jiraApiProperties;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final WebClient webClient;

    /**
     * Constructeur pour initialiser le service avec les propriétés de l'API Jira et configurer le client Web.
     *
     * @param jiraProps Les propriétés de l'API Jira (URL de l'API, identifiants).
     */
    public JiraDataService(JiraApiProperties jiraProps) {
        this.jiraApiProperties = jiraProps;
        this.webClient = WebClient.builder()
                .baseUrl(jiraApiProperties.getApiUrl())
                .defaultHeaders(headers -> headers.setBasicAuth(jiraApiProperties.getUsername(), jiraApiProperties.getApiToken()))
                .build();
    }

    /**
     * Méthode principale pour récupérer les détails d'une issue Jira et retourner les critères d'acceptation.
     *
     * @param request Objet contenant la clé de l'issue Jira.
     * @return Une réponse contenant la clé de l'issue et ses critères d'acceptation.
     */
    @Override
    public Response apply(Request request) {
        try {
            log.info("Récupération des détails pour l'issue: {}", request.storyKey());

            String issueEndpoint = "/rest/api/2/issue/" + request.storyKey();

            String jsonResponse = webClient.get()
                    .uri(issueEndpoint)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Journaliser la réponse JSON complète
            log.info("Réponse JSON complète de Jira: {}", jsonResponse);

            // Analyse de la réponse JSON pour extraire les critères d'acceptation
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            String description = extractAcceptanceCriteria(rootNode);

            // Créer et retourner la réponse
            return new Response(request.storyKey(), description);

        } catch (WebClientResponseException e) {
            log.error("Erreur lors de la réponse de l'API Jira: {}", e.getResponseBodyAsString(), e);
            throw new RuntimeException("Échec de la récupération des détails de l'issue Jira pour: " + request.storyKey(), e);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des détails de l'issue Jira: {}", request.storyKey(), e);
            throw new RuntimeException("Échec de la récupération des détails de l'issue Jira pour: " + request.storyKey(), e);
        }
}


    /**
     * Méthode privée pour extraire les critères d'acceptation à partir de l'objet JSON renvoyé par l'API Jira.
     *
     * @param rootNode L'objet JSON contenant les détails de l'issue.
     * @return Les critères d'acceptation sous forme de chaîne de caractères.
     */
    private String extractAcceptanceCriteria(JsonNode rootNode) {
        String acceptanceCriteria = "";

        try {
            // Naviguer jusqu'au champ "description" dans l'objet "fields"
            JsonNode descriptionNode = rootNode.path("fields").path("description");

            // Vérifier si le nœud description existe et n'est pas nul
            if (descriptionNode.isMissingNode() || descriptionNode.isNull()) {
                log.warn("Le champ 'description' est manquant ou nul.");
            } else {
                // Extraire la description directement sous forme de chaîne
                acceptanceCriteria = descriptionNode.asText();
            }
        } catch (Exception e) {
            log.error("Erreur lors de l'extraction des critères d'acceptation", e);
        }

        log.info("Critères d'acceptation extraits: {}", acceptanceCriteria.trim());
        return acceptanceCriteria.trim();
    }

    /**
     * Classe interne pour représenter une requête contenant la clé de l'issue Jira.
     *
     * @param storyKey La clé unique de l'issue Jira.
     */
    public record Request(String storyKey) {}

    /**
     * Classe interne pour représenter une réponse contenant la clé de l'issue et ses critères d'acceptation.
     *
     * @param storyKey La clé unique de l'issue Jira.
     * @param acceptanceCriteria Les critères d'acceptation de l'issue.
     */
    public record Response(String storyKey, String acceptanceCriteria) {}
}
