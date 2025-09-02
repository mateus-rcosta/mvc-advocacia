package com.unifil.advocacia.gerenciador.cliente.dto;

public record PutCliente(
    String nome,
    String email,
    String telefone,
    String documento
) {
    
}