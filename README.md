# Todo Manager Service

The Todo Manager Service is a RESTful API designed to manage todo details.

## Table of Contents

- [Features](#features)
- [Setup & Installation](#setup--installation)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)


## Features

- **CRUD Operations**: Manage todo details with Create, Read, Update, and Delete operations.

## Setup & Installation

1. **Clone the Repository**:
    ```bash
    git clone https://github.com/marwaGoda/todo-manager.git
    ```

2. **Navigate to the project directory**:
    ```bash
    cd todo-manager
    ```

3. **Install Dependencies**:
    ```bash
    gradle build
    ```

4. **Run the Service**:
    ```bash
    gradle bootRun
    ```

## API Endpoints

- `GET /todos`: Retrieve all todos.
- `POST /todos`: Create a new todo.
- `PUT /todos/{id}`: Update a todo's details.
- `DELETE /todos/{id}`: Delete a todo.

## Testing

- **Unit Tests**:
    ```bash
    gradle test
    ```

## API Documentation

You can view and test the API using  Swagger documentation.

To access the API documentation:

Download the  [swagger.yaml](https://github.com/marwaGoda/todo-manager/tree/master/src/main/resources/swagger.yaml) file.
Visit [Swagger Editor](https://editor.swagger.io/).
Upload the swagger.json file or paste its contents into the editor.
The API documentation will be visualized, and you can interact with the API endpoints directly from there.