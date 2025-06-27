# Application de génération de cas de test pour les Stories Jira avec intégration du modèle Ollama

## Vue d'ensemble

L'application de génération de cas de test pour les Stories Jira est une application Spring Boot conçue pour récupérer les détails des user stories de Jira et générer des cas de test basés sur les critères d'acceptation. Elle s'intègre avec le modèle d'intelligence artificielle Ollama pour traiter les entrées en langage naturel et générer automatiquement des cas de test détaillés.

## Structure du projet

- **JiraInquiryController** :
  - Gère les requêtes HTTP pour les demandes d'informations sur les stories Jira.
  - Utilise le modèle d'IA Ollama pour traiter les critères d'acceptation et générer les cas de test pertinents.

- **JiraFunctionConfig** :
  - Configure et fournit le bean `JiraDataService`.
  - Configure la fonction Jira utilisée par le modèle d'IA pour récupérer les détails des stories Jira et générer les cas de test.

- **JiraDataService** :
  - Gère la communication avec l'API Jira pour récupérer les détails des user stories, y compris les critères d'acceptation.
  - Lit les identifiants et l'URL de l'API Jira à partir des propriétés de configuration.
  - Analyse les réponses de l'API Jira pour extraire les informations pertinentes.

- **JiraApiProperties** :
  - Stocke les identifiants de l'API Jira (nom d'utilisateur, jeton API) et l'URL de base.
  - Fournit les propriétés de configuration pour `JiraDataService`.

## Configuration

1. **Configurer votre environnement** :
   - Assurez-vous que Java 17 et Gradle sont installés.

2. **Configurer les propriétés de l'application** :
   - Mettez à jour le fichier `src/main/resources/application.properties` avec vos identifiants Jira :
     ```properties
     jira.username=email@domain.com
     jira.apiToken=VOTRE_JIRA_API_TOKEN
     jira.apiUrl=https://jira.atlassian.net/v1
     ```

## Exécution de l'application

1. **Construire et démarrer l'application** :
   ```sh
   ./gradlew clean build bootRun

2. **Envoyer une requête à l'application via Postman/Httpie ou tout autre outil** :
    ```sh
    GET http://localhost:8080/api/v1/jira-story?storyKey=KAN-3

## CREDITS
Alex Alkhatib