package com.unifil.advocacia.gerenciador.cliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.unifil.advocacia.gerenciador.cliente.dto.PostCliente;
import com.unifil.advocacia.gerenciador.cliente.dto.PutCliente;
import com.unifil.advocacia.gerenciador.cliente.model.Cliente;
import com.unifil.advocacia.gerenciador.cliente.service.ClienteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> salvarCliente(@RequestBody PostCliente dto) {
        Cliente clienteSalvo = clienteService.salvarCliente(dto);
        return new ResponseEntity<>(clienteSalvo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable UUID id) {
        Cliente cliente = clienteService.buscarClientePorId(id);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizarCliente(@PathVariable UUID id, @RequestBody PutCliente dto) {
        Cliente clienteAtualizado = clienteService.atualizarCliente(id, dto);
        return new ResponseEntity<>(clienteAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> excluirCliente(@PathVariable UUID id) {
        clienteService.excluirCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}