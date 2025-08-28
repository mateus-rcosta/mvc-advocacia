package com.unifil.advogacia.gerenciador.cliente.dto;

public record PutCliente(
    String nome,
    String email,
    String telefone,
    String documento
) {
    
}