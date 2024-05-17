# Kanban Web App
This is a simple Kanban web app that allows users to create, update, and delete tasks. The app is built using Java Spring Boot for the backend and Angular for the frontend.

## Getting Started
### Prerequisites
- Java 17
- Maven
- Node.js

### Running the Backend
1. Clone the repository
2. Run the backend
    ```bash
    mvn clean install spring-boot:run
    ```

### Running the Frontend 
Firstly launch the backend otherwise the frontend will not work.
1. Install Angular CLI
    ```bash
    npm install -g @angular/cli
    ```
2. Run the frontend
    ```bash
    cd src/main/angular
    npm install # Maven do it alredy but if you have some problem you can do it manually
    ng serve --proxy-config proxy.conf.json
    ```

## Built With
- [Java Spring Boot](https://spring.io/projects/spring-boot) - Backend framework
- [Angular](https://angular.io/) - Frontend framework
- [Derby](https://db.apache.org/derby/) - Database management system
- [Maven](https://maven.apache.org/) - Dependency management
- [Node.js](https://nodejs.org/) - JavaScript runtime