package com.doers.todomanager.service;


import com.doers.todomanager.mapper.TodoMapper;
import com.doers.todomanager.model.TodoDTO;
import com.doers.todomanager.entity.TodoEntity;
import com.doers.todomanager.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TodoServiceTest {

    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private TodoMapper todoMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTodo() {
        TodoDTO newTodoDTO = new TodoDTO(null, "Test Todo", true,
                LocalDateTime.now(), LocalDateTime.now());

        TodoEntity todoEntity = new TodoEntity(null, "Test Todo", true,
                LocalDateTime.now());

        TodoEntity savedTodoEntity = new TodoEntity(1L, "Test Todo", true,
                LocalDateTime.now());

        TodoDTO savedTodoDTO = new TodoDTO(1L, "Test Todo", true,
                LocalDateTime.now(), LocalDateTime.now());

        when(todoMapper.toEntity(newTodoDTO)).thenReturn(todoEntity);
        when(todoRepository.save(todoEntity)).thenReturn(savedTodoEntity);
        when(todoMapper.toDTO(savedTodoEntity)).thenReturn(savedTodoDTO);

        TodoDTO result = todoService.createTodo(newTodoDTO);

        assertEquals(savedTodoDTO, result);
    }

    @Test
    public void testGetTodoByIdWhenTodoExists() {
        TodoEntity existingTodoEntity = new TodoEntity(1L, "Test Todo",  true,
                LocalDateTime.now());
        TodoDTO existingTodoDTO = new TodoDTO(null, "Test Todo", true,
                LocalDateTime.now(), LocalDateTime.now());
        when(todoRepository.findById(1L)).thenReturn(Optional.of(existingTodoEntity));
        when(todoMapper.toDTO(existingTodoEntity)).thenReturn(existingTodoDTO);

        Optional<TodoDTO> result = todoService.getTodoById(1L);

        assertTrue(result.isPresent());
        assertEquals(existingTodoDTO, result.get());
    }

    @Test
    public void testGetTodoByIdWhenTodoDoesNotExist() {
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<TodoDTO> result = todoService.getTodoById(1L);

        assertFalse(result.isPresent());
    }
    @Test
    public void testGetAllTodos() {
        List<TodoDTO> expectedTodoDTOS = List.of(
                new TodoDTO(1L, "Todo1", true,
                        LocalDateTime.now(), LocalDateTime.now()),
                new TodoDTO(2L, "Todo2", true,
                        LocalDateTime.now(), LocalDateTime.now())
        );

        List<TodoEntity> expectedTodoEntities = List.of(
                new TodoEntity(1L, "Todo1", true,
                        LocalDateTime.now()),
                new TodoEntity(2L, "Todo2", true,
                        LocalDateTime.now())
        );

        when(todoRepository.findAll()).thenReturn(expectedTodoEntities);
        when(todoMapper.toDTO(expectedTodoEntities.get(0))).thenReturn(expectedTodoDTOS.get(0));
        when(todoMapper.toDTO(expectedTodoEntities.get(1))).thenReturn(expectedTodoDTOS.get(1));
        Collection<TodoDTO> result = todoService.getAllTodos();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.containsAll(expectedTodoDTOS));
    }


    @Test
    public void testUpdateTodoWhenExists() {
        TodoDTO updatedTodoDTO = new TodoDTO(1L, "Updated Todo", true,
                LocalDateTime.now(), LocalDateTime.now());
        TodoEntity updatedTodoEntity = new TodoEntity(1L, "Updated Todo", true,
                LocalDateTime.now());

        when(todoRepository.findById(1L)).thenReturn(Optional.of(updatedTodoEntity));
        when(todoRepository.save(updatedTodoEntity)).thenReturn(updatedTodoEntity);
        when(todoMapper.toDTO(updatedTodoEntity)).thenReturn(updatedTodoDTO);
        Optional<TodoDTO> result = todoService.updateTodo(1L, updatedTodoDTO);

        assertTrue(result.isPresent());
        assertEquals(updatedTodoDTO, result.get());
    }

    @Test
    public void testDeleteTodo() {
        doNothing().when(todoRepository).deleteById(1L);

        todoService.deleteTodo(1L);

        verify(todoRepository, times(1)).deleteById(1L);
    }


}