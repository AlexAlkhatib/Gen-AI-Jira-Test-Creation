package com.asecurityguru.ollamarestapi.functions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

/**
 * Classe de configuration pour initialiser la fonction permettant de récupérer
 * les détails des stories Jira et de générer des cas de test associés.
 */
@Configuration
public class JiraFunctionConfig {

    private final JiraApiProperties jiraApiProperties;

    /**
     * Constructeur pour injecter les propriétés de configuration Jira.
     *
     * @param jiraApiProperties Les propriétés configurées pour l'API Jira.
     */
    public JiraFunctionConfig(JiraApiProperties jiraApiProperties) {
        this.jiraApiProperties = jiraApiProperties;
    }

    /**
     * Définit un bean Spring qui fournit une fonction pour récupérer les détails d'une story Jira
     * et générer les cas de test associés.
     *
     * @return Une fonction prenant une requête Jira et renvoyant une réponse Jira.
     */
    @Bean
    @Description("Fournit une fonction pour récupérer les détails des stories Jira et générer des cas de test.")
    public Function<JiraDataService.Request, JiraDataService.Response> jiraFunction()  {
        // Valider les propriétés de configuration Jira
        validateJiraConfigProperties(jiraApiProperties);

        // Créer et retourner une nouvelle instance de JiraDataService
        return new JiraDataService(jiraApiProperties);
    }

    /**
     * Valide les propriétés de configuration de l'API Jira.
     * Vérifie que l'URL, le jeton d'API et le nom d'utilisateur sont correctement configurés.
     *
     * @param properties Les propriétés Jira à valider.
     * @throws IllegalArgumentException Si une des propriétés requises est manquante ou vide.
     */
    private void validateJiraConfigProperties(JiraApiProperties properties) {
        if (properties.getApiUrl() == null || properties.getApiUrl().isEmpty()) {
            throw new IllegalArgumentException("L'URL de l'API Jira doit être fournie.");
        }
        if (properties.getApiToken() == null || properties.getApiToken().isEmpty()) {
            throw new IllegalArgumentException("Le jeton d'accès API Jira doit être fourni.");
        }
        if (properties.getUsername() == null || properties.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Le nom d'utilisateur Jira doit être fourni.");
        }
    }
}
