package com.unifil.advogacia.gerenciador.cliente.service;

import com.unifil.advogacia.gerenciador.cliente.model.Cliente;
import com.unifil.advogacia.gerenciador.cliente.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvarCliente(Cliente cliente) {
        if (cliente.getId() != null && clienteRepository.existsById(cliente.getId())) {
            throw new RuntimeException("Cliente já existe com o ID informado");
        }
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarClientePorId(UUID id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID informado"));
    }

    public Cliente atualizarCliente(Cliente cliente) {
        if (!clienteRepository.existsById(cliente.getId())) {
            throw new RuntimeException("Cliente não encontrado com o ID informado");
        }
        return clienteRepository.save(cliente);
    }

    public void excluirCliente(UUID id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com o ID informado");
        }
        clienteRepository.deleteById(id);
    }
}