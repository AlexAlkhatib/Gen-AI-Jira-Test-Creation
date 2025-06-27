package com.asecurityguru.ollamarestapi.functions;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Classe de configuration pour les propriétés de l'API Jira.
 * Permet de mapper les propriétés définies dans le fichier de configuration 
 * (par exemple, `application.yml` ou `application.properties`) sous le préfixe `jira`.
 */
@ConfigurationProperties(prefix = "jira")
public class JiraApiProperties {

    private String username;
    private String apiToken;
    private String apiUrl;

    /**
     * Obtient le nom d'utilisateur configuré pour l'API Jira.
     *
     * @return Le nom d'utilisateur sous forme de chaîne.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Définit le nom d'utilisateur pour l'API Jira.
     *
     * @param username Le nom d'utilisateur à utiliser pour l'authentification.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtient le jeton API configuré pour l'authentification auprès de l'API Jira.
     *
     * @return Le jeton API sous forme de chaîne.
     */
    public String getApiToken() {
        return apiToken;
    }

    /**
     * Définit le jeton API pour l'authentification auprès de l'API Jira.
     *
     * @param apiToken Le jeton d'accès API.
     */
    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    /**
     * Obtient l'URL de base de l'API Jira.
     *
     * @return L'URL de base sous forme de chaîne.
     */
    public String getApiUrl() {
        return apiUrl;
    }

    /**
     * Définit l'URL de base de l'API Jira.
     *
     * @param apiUrl L'URL de base à utiliser pour les requêtes API.
     */
    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    /**
     * Retourne une représentation textuelle des propriétés de l'API Jira.
     * Cette méthode est principalement utilisée pour faciliter le débogage.
     *
     * @return Une chaîne contenant les informations des propriétés.
     */
    @Override
    public String toString() {
        return "JiraApiProperties{" +
                "username='" + username + '\'' +
                ", apiToken='" + apiToken + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                '}';
    }
}
