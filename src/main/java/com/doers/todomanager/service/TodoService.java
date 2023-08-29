package com.doers.todomanager.service;

import com.doers.todomanager.entity.TodoEntity;
import com.doers.todomanager.mapper.TodoMapper;
import com.doers.todomanager.model.TodoDTO;
import com.doers.todomanager.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoMapper todoMapper;

    public TodoDTO createTodo(TodoDTO todoDTO) {
        TodoEntity entity = todoMapper.toEntity(todoDTO);
        TodoEntity savedEntity = todoRepository.save(entity);
        return todoMapper.toDTO(savedEntity);
    }

    public Optional<TodoDTO> getTodoById(Long id) {
        return todoRepository.findById(id)
                .map(todoMapper::toDTO);
    }

    public List<TodoDTO> getAllTodos() {
        return todoRepository.findAll().stream()
                .map(todoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<TodoDTO> updateTodo(Long id, TodoDTO updatedTodoDTO) {
        var existingTodoEntity = todoRepository.findById(id);
        if (existingTodoEntity.isPresent()) {
            TodoEntity entityToUpdate = existingTodoEntity.get();
            entityToUpdate.setTitle(updatedTodoDTO.getTitle());
            entityToUpdate.setCompleted(updatedTodoDTO.isCompleted());
            entityToUpdate.setCreateTime(updatedTodoDTO.getCreateTime());
            entityToUpdate.setUpdateTime(updatedTodoDTO.getUpdateTime());

            TodoEntity updatedEntity = todoRepository.save(entityToUpdate);
            return Optional.of(todoMapper.toDTO(updatedEntity));
        } else {
            return Optional.empty();
        }
    }

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}