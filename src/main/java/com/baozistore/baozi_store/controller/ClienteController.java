package com.baozistore.baozi_store.controller;

import com.baozistore.baozi_store.model.Cliente;
import com.baozistore.baozi_store.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    //Cadastro dos Clientes
    @PostMapping
    public Cliente cadastrar(@RequestBody Cliente cliente) {
        return  clienteRepository.save(cliente);
    }
    //Aqui ele lista todos os Clientes
    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
    //Aqui consulta cada cliente a partir do ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return clienteRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }
    //Aqui ele exclui um cadastro de algum cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}