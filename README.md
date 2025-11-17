# 🧠 **Jira Test Case Generator — Spring Boot + Ollama AI Integration**

Cette application permet de **générer automatiquement des cas de test** à partir des **user stories Jira**.
Elle combine la puissance de **Spring Boot**, l’intégration **API Jira**, et le modèle d’IA **Ollama** pour produire des cas de test détaillés et cohérents à partir des critères d’acceptation écrits en langage naturel.

Il s’agit d’un projet **personnel**, démontrant mes compétences Fullstack + IA appliquée aux workflows Dev/Test.


## 🎯 **Objectif du projet**

* Interroger automatiquement une story Jira via son *story key*
* Extraire les informations essentielles : titre, description, critères d’acceptation
* Envoyer ces éléments au modèle IA **Ollama**
* Générer un ensemble précis, complet et exploitable de **cas de test fonctionnels**
* Permettre à une équipe QA de documenter plus rapidement et plus efficacement leurs tests


## 🧰 **Stack Technique**

* **Java 17**
* **Spring Boot 3+**
* **Gradle**
* **Ollama Local AI Model**
* **Spring Web / RestController**
* **Jira API REST v1**
* **Configuration Properties**
* **JSON Processing (Jackson)**


## 🏗️ **Architecture du projet**

Le projet suit une architecture claire et modulaire :

```
src/main/java/com.project
 ├── controller/
 │     └── JiraInquiryController.java
 ├── config/
 │     └── JiraFunctionConfig.java
 ├── service/
 │     └── JiraDataService.java
 ├── properties/
 │     └── JiraApiProperties.java
 └── Application.java
```

### **1. JiraInquiryController**

* Expose l’endpoint public REST
* Reçoit la clé de story (ex : `KAN-3`)
* Récupère la story → critères d’acceptation
* Appelle **Ollama** pour générer les cas de test
* Retourne les cas de test sous forme JSON

### **2. JiraFunctionConfig**

* Déclare le bean de service Jira
* Configure la fonction utilisée par le modèle IA Ollama
* Injecte les dépendances nécessaires

### **3. JiraDataService**

* Gère la communication avec **l’API Jira Cloud**
* Appelle `/rest/api/3/issue/{storyKey}`
* Extrait description, AC, champs personnalisés
* Retourne des données prêtes à être traitées par l’IA

### **4. JiraApiProperties**

* Contient :

  * `jira.username`
  * `jira.apiToken`
  * `jira.apiUrl`
* Fournit les propriétés sécurisées pour JiraDataService


## ⚙️ **Configuration**

### ✔ Pré-requis

* Java 17
* Gradle
* Ollama installé localement
* Modèle AI téléchargé (ex : `ollama pull mistral`)

### ✔ Configurer les identifiants Jira

Dans `src/main/resources/application.properties` :

```
jira.username=votre.email@domain.com
jira.apiToken=VOTRE_API_TOKEN
jira.apiUrl=https://votre-instance.atlassian.net/rest/api/3
```

> ⚠️ **Ne jamais commit vos tokens Jira** — utiliser un `.env` ou un vault GitHub Actions.


## ▶️ **Exécution du projet**

### **1. Build + Run**

```bash
./gradlew clean build bootRun
```

### **2. Tester l’API**

Via Postman / Curl / Httpie :

**GET**

```
http://localhost:8080/api/v1/jira-story?storyKey=KAN-3
```

### Exemple de réponse

```json
{
  "storyKey": "KAN-3",
  "title": "User logs in",
  "generatedTestCases": [
    {
      "id": "TC001",
      "description": "L'utilisateur saisit un email valide et un mot de passe valide",
      "steps": [...],
      "expectedResult": "Connexion réussie"
    },
    ...
  ]
}
```


## 🤖 **Fonctionnement de l’IA (Ollama)**

Le modèle Ollama est utilisé pour :

* analyser les critères d’acceptation
* reformuler les comportements attendus
* générer des cas de test structurés
* couvrir cas positifs + négatifs + erreurs + edge cases

Par exemple :

```text
Generate structured functional test cases based on the Jira acceptance criteria:
- AC1: User must be logged in
- AC2: Display error on invalid password
```


## 🧠 Compétences démontrées

✔ Développement Spring Boot avancé
✔ Intégration API REST (Jira Cloud)
✔ Consommation d’un modèle IA local (Ollama)
✔ Génération de cas de test automatisée (QA Engineering)
✔ Clean architecture & séparation des responsabilités
✔ Gestion sécurisée des propriétés de configuration
✔ Construction d’un pipeline Dev/Test intelligent
✔ Automatisation d’un flux métier réel


## 🚀 Améliorations possibles

* Ajouter une interface Web (React / Angular)
* Ajouter un export PDF/CSV des cas de test
* Support pour plusieurs modèles Ollama
* Génération de *Gherkin* (Given/When/Then)
* Intégration GitLab/Jenkins pour pipeline CI
* Historisation des générations (SQL/PostgreSQL)


## 👤 **Crédits**

Projet réalisé par **Alex Alkhatib**.


## 📄 Licence
MIT License
Copyright (c) 2025 Alex Alkhatib


