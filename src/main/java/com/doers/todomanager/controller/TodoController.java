package com.doers.todomanager.controller;

import com.doers.todomanager.model.TodoDTO;
import com.doers.todomanager.service.TodoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/todos")
@Api(value = "Todo Management System", description = "Operations pertaining to todos in the Todo Management System")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @ApiOperation(value = "Add a new todo")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added the todo"),
            @ApiResponse(code = 400, message = "Invalid input data"),
            @ApiResponse(code = 403, message = "Operation forbidden")
    })
    @PostMapping
    public ResponseEntity<TodoDTO> create(@RequestBody TodoDTO todoDTO) {
        return new ResponseEntity<>(todoService.createTodo(todoDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "View a list of available todos", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping
    public ResponseEntity<Collection<TodoDTO>> getAll() {
        return new ResponseEntity<>(todoService.getAllTodos(), HttpStatus.OK);
    }

    @ApiOperation(value = "Get a todo by its ID", response = TodoDTO.class)
    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getById(@PathVariable Long id) {
        var todo = todoService.getTodoById(id);
        return todo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Update an existing todo by ID", response = TodoDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the todo"),
            @ApiResponse(code = 400, message = "Invalid input data"),
            @ApiResponse(code = 404, message = "Todo not found"),
            @ApiResponse(code = 403, message = "Operation forbidden")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> update(@PathVariable Long id, @RequestBody TodoDTO todoDTO) {
        var updatedTodo = todoService.updateTodo(id, todoDTO);
        return updatedTodo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Delete a todo by its ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted the todo"),
            @ApiResponse(code = 404, message = "Todo not found"),
            @ApiResponse(code = 403, message = "Operation forbidden")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var todo = todoService.getTodoById(id);
        if (todo.isPresent()) {
            todoService.deleteTodo(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}