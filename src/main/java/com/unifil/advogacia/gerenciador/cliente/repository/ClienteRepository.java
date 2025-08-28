package com.unifil.advogacia.gerenciador.cliente.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unifil.advogacia.gerenciador.cliente.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID>{
    
}
