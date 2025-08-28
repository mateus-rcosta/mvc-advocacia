package com.unifil.advogacia.gerenciador.funcionario.dto;

import com.unifil.advogacia.gerenciador.funcionario.enums.Funcao;

public record PutFuncionario(
    String nome,
    String email,
    String senha,
    Funcao funcao
) {
    
}
