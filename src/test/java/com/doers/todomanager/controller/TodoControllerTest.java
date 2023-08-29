package com.doers.todomanager.controller;



import com.doers.todomanager.model.TodoDTO;
import com.doers.todomanager.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TodoControllerTest {

    @InjectMocks
    private TodoController todoController;

    @Mock
    private TodoService todoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTodo() {
        TodoDTO newTodoDTO = new TodoDTO(null, "Test Todo", true,
                LocalDateTime.now(), LocalDateTime.now());
        TodoDTO savedTodoDTO = new TodoDTO(1L, "Test Todo", true,
                LocalDateTime.now(), LocalDateTime.now());

        when(todoService.createTodo(newTodoDTO)).thenReturn(savedTodoDTO);

        ResponseEntity<TodoDTO> response = todoController.create(newTodoDTO);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(savedTodoDTO, response.getBody());
    }
    @Test
    public void testGetAllTodos() {
        List<TodoDTO> expectedTodoDTOS = List.of(
                new TodoDTO(1L, "Todo1", true,
                        LocalDateTime.now(), LocalDateTime.now()),
                new TodoDTO(2L, "Todo2", true,
                        LocalDateTime.now(), LocalDateTime.now() )
        );

        when(todoService.getAllTodos()).thenReturn(expectedTodoDTOS);

        ResponseEntity<Collection<TodoDTO>> response = todoController.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().containsAll(expectedTodoDTOS));
    }

    @Test
    public void testUpdateTodoWhenExists() {
        TodoDTO updatedTodoDTO = new TodoDTO(1L, "Updated Todo", true,
                LocalDateTime.now(), LocalDateTime.now());

        when(todoService.updateTodo(1L, updatedTodoDTO)).thenReturn(Optional.of(updatedTodoDTO));

        ResponseEntity<TodoDTO> response = todoController.update(1L, updatedTodoDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedTodoDTO, response.getBody());
    }
    @Test
    public void testGetByIdWhenTodoExists() {
        TodoDTO existingTodoDTO = new TodoDTO(1L, "Test Todo", true,
                LocalDateTime.now(), LocalDateTime.now());

        when(todoService.getTodoById(1L)).thenReturn(Optional.of(existingTodoDTO));

        ResponseEntity<TodoDTO> response = todoController.getById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(existingTodoDTO, response.getBody());
    }

    @Test
    public void testGetByIdWhenTodoDoesNotExist() {
        when(todoService.getTodoById(1L)).thenReturn(Optional.empty());

        ResponseEntity<TodoDTO> response = todoController.getById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testDeleteTodo() {
        TodoDTO existingTodoDTO = new TodoDTO(1L, "Test Todo", true,
                LocalDateTime.now(), LocalDateTime.now());
        when(todoService.getTodoById(1L)).thenReturn(Optional.of(existingTodoDTO));

        ResponseEntity<?> response = todoController.delete(1L);

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void testDeleteTodoWhenNotExists() {
        when(todoService.getTodoById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = todoController.delete(1L);

        assertEquals(404, response.getStatusCodeValue());
    }



}