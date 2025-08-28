package com.unifil.advogacia.gerenciador.http;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {
    private String mensagem;
    private int status;
    private LocalDateTime data;
    private T detalhes;
}

