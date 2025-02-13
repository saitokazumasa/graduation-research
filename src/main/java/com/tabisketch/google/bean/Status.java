package com.tabisketch.google.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Status {
    private Integer code;
    private String message;
    private Object[] details;
}
