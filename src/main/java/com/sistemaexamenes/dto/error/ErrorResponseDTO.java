package com.sistemaexamenes.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDTO {

    private LocalDateTime timestamp;

    private Integer status;

    private String error;

    private String message;
}