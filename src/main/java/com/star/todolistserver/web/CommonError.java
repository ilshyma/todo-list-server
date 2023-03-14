package com.star.todolistserver.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonError {
    private String message;
    private Integer code;
}
