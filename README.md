Projet MDD Full-Stack

Présentation

Application full-stack composée de :

* Back-end : Spring Boot 
* Front-end : Angular 

📦 Fonctionnalités principales

- **Gestion des utilisateurs**
  - Inscription et connexion via e-mail ou nom d’utilisateur et mot de passe.
  - Consultation et modification du profil (e-mail, nom d’utilisateur, mot de passe).
  - Déconnexion.

- **Authentification JWT**
  - Sécurisation des endpoints REST.
  - Gestion des tokens pour persistance des sessions.

- **Gestion des abonnements**
  - Consultation de tous les thèmes disponibles.
  - Abonnement et désabonnement aux thèmes.
  - Fil d’actualité personnalisé selon les abonnements.

- **API REST documentée via Swagger UI**
  - Documentation interactive de tous les endpoints.
  - Test des appels REST directement depuis l’interface Swagger ou postman.

🚀Back-end (Spring Boot)

Nom du projet : mdd-api
Java : 17
Spring Boot : 3.5.0

Stack & dépendances

* Spring Data JPA
* Spring Security
* MySQL (runtime)
* H2 (tests en mémoire)
* JWT (io.jsonwebtoken 0.11.5)
* MapStruct (1.5.5.Final)
* Lombok (1.18.34)
* OpenAPI / Swagger UI (springdoc-openapi-starter-webmvc-ui 2.7.0)

🧪Tests & couverture

* Les test sont réalisés avec JUNIT
* Pour lancer les tests : 
mvn test  


Installer les dépendances  : 
mvn clean install  

Lancer le back-end  :  
mvn spring-boot:run

* API : http://localhost:3001
* Swagger UI : http://localhost:3001/swagger-ui/index.html



🚀Front-end (Angular)

Nom du projet : front
Angular : 14.1.3
UI : Angular Material (14.2.5) + Flex Layout (14.0.0-beta.40) + RxJS (7.5.0)


🧪 Tests

* Jest (jest)
* Pour lancer les tests lancer la commande :  
npm run test:jest




Installer les dépendances :  
npm install
    
Lancer l'application :   
ng serve

* Application accessible sur : http://localhost:4200

Authentification

* JWT pour sécuriser les endpoints

Documentation

* API documentée via Swagger UI :http://localhost:3001/swagger-ui/index.html

Scripts utiles




#  Note
	initialisation du jeu de données
Lorsque vous lancerrez l'application pour la premiére fois. 
JPA contruira la base de données grace à la couche model, une fois cela réalisé, il vous faudra initialiser l'application avec une jeu de test notemment les valeurs des themes.
Pour cela vous trouverrez dans ressources\sql\scriptInitialisation.sql un script d'initialisation de la base de données.
Celui-ci permet de créer un utilisateur puis un ensemble de theme qui serviront d'exemple dans l'application.
L'utilisateur de test est 
email -> mariusxx@123.fr
password -> Test1234!


