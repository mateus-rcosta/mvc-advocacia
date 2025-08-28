package com.unifil.advogacia.gerenciador.funcionario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unifil.advogacia.gerenciador.funcionario.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{
    boolean existsByEmail(String email);
}
