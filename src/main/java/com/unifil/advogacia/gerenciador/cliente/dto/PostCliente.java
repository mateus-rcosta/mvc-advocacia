package com.unifil.advogacia.gerenciador.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostCliente(
    @NotBlank @NotNull
    String nome,
    @NotBlank @NotNull @Email
    String email,
    @NotBlank @NotNull 
    String telefone,
    @NotBlank @NotNull 
    String documento
) {
    
}
