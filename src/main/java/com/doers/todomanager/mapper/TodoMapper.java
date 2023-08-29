package com.doers.todomanager.mapper;

import org.springframework.stereotype.Component;
import com.doers.todomanager.entity.TodoEntity;
import com.doers.todomanager.model.TodoDTO;
@Component
public class TodoMapper {

    public TodoDTO toDTO(TodoEntity entity) {
        return new TodoDTO(entity.getId(), entity.getTitle(), entity.isCompleted(),
                 entity.getCreateTime(), entity.getUpdateTime());
    }
    public TodoEntity toEntity(TodoDTO dto) {
        TodoEntity entity = new TodoEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setCompleted(dto.isCompleted());
        entity.setCreateTime(dto.getCreateTime());
        entity.setUpdateTime(dto.getUpdateTime());
        return entity;
    }
}
