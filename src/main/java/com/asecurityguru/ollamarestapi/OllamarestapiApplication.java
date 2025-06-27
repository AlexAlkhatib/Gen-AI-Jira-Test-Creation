package com.asecurityguru.ollamarestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.asecurityguru.ollamarestapi.functions.JiraApiProperties;

/**
 * Classe principale de l'application Spring Boot.
 * Point d'entrée pour démarrer l'application.
 * Configure le chargement des composants et active les propriétés de configuration pour l'API Jira.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.asecurityguru.ollamarestapi")
@EnableConfigurationProperties(JiraApiProperties.class)
public class OllamarestapiApplication {
	/**
     * Méthode principale pour démarrer l'application.
     * Initialise le contexte Spring et lance l'application en utilisant les arguments passés.
     *
     * @param args Les arguments de la ligne de commande.
     */
	public static void main(String[] args) {
		SpringApplication.run(OllamarestapiApplication.class, args);
	}

}
