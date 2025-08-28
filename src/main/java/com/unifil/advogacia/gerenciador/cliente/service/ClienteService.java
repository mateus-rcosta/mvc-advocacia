package com.unifil.advogacia.gerenciador.cliente.service;

import com.unifil.advogacia.gerenciador.cliente.dto.PostCliente;
import com.unifil.advogacia.gerenciador.cliente.dto.PutCliente;
import com.unifil.advogacia.gerenciador.cliente.model.Cliente;
import com.unifil.advogacia.gerenciador.cliente.repository.ClienteRepository;
import com.unifil.advogacia.gerenciador.exception.BadRequestException;
import com.unifil.advogacia.gerenciador.exception.NotFoundException;
import com.unifil.advogacia.gerenciador.lib.CpfCnpjUtil;
import com.unifil.advogacia.gerenciador.lib.TelefoneUtil;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvarCliente(PostCliente dto) {
        if(!CpfCnpjUtil.isValidCpfCnpj(dto.documento())){
            throw new BadRequestException("Erro de validação", Map.of("documentoIncorreto", true));
        }
        if(clienteRepository.existsByDocumento(dto.documento())){
            throw new BadRequestException("Erro de validação", Map.of("documentoExistente", true));
        }
        Cliente cliente = new Cliente(dto.nome(), dto.telefone(), dto.email(), dto.documento());
        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarClientePorId(UUID id) {
        return clienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Cliente não encontrado com o ID informado", Map.of("naoExiste", true)));
    }

    @Transactional
    public Cliente atualizarCliente(UUID id, PutCliente dto) {
        HashMap<String, Boolean> validacao = new HashMap<String, Boolean>();
        existsById(id);

        Cliente cliente = new Cliente();
        cliente.setId(id);
        if (!dto.nome().equals("")) {
            cliente.setNome(dto.nome());
        }

        if (!dto.email().equals("")) {
            cliente.setEmail(dto.email());
        }

        if (!dto.telefone().equals("")) {
            if (!TelefoneUtil.isTelefoneValido(dto.telefone())) validacao.put("telefoneInvalido", true);
            cliente.setTelefone(TelefoneUtil.normalizarTelefone(dto.telefone()));
        }

        if (!dto.documento().equals("")) {
            if(!CpfCnpjUtil.isValidCpfCnpj(dto.documento())) validacao.put("documentoInvalido", true);
            cliente.setDocumento(CpfCnpjUtil.formatCpfCnpj(dto.documento()));
        }
        if (validacao.isEmpty()) {
            throw new BadRequestException("Erro de validação: ", validacao);
        }
        return clienteRepository.save(cliente);
    }

    @Transactional
    public void excluirCliente(UUID id) {
        existsById(id);
        clienteRepository.deleteById(id);
    }

    private void existsById(UUID id){
        if (!clienteRepository.existsById(id)) {
            throw new NotFoundException("Cliente não encontrado com o ID informado", Map.of("naoExiste", true));
        }
    }
}