package com.doers.todomanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TodoDTO {
    private Long id;
    private String title;
    private boolean completed;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}