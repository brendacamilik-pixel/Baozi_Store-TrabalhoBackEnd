package com.baozistore.baozi_store.controller;

import com.baozistore.baozi_store.model.Pedido;
import com.baozistore.baozi_store.repository.PedidoRepository;
import com.baozistore.baozi_store.repository.ClienteRepository;
import com.baozistore.baozi_store.repository.ProdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    //Aqui é para cadastrar o pedido
    @PostMapping
    public ResponseEntity<Pedido> cadastrar(@RequestBody Pedido pedido) {
        if (pedido.getClienteId() == null ||
            pedido.getProdutoId() == null ||
            pedido.getQuantidade() == null ||
            pedido.getQuantidade() <= 0) {
                return ResponseEntity.badRequest().build();
        }
        if (!clienteRepository.existsById(pedido.getClienteId()) ||
            !produtoRepository.existsById(pedido.getProdutoId())) {
                return ResponseEntity.badRequest().build();
            }
        Pedido novoPedido = pedidoRepository.save(pedido);
        return ResponseEntity.ok(novoPedido);        
    }
    //Aqui é para listar todos os pedidos
    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }
    //Aqui é para consultar o pedido pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }
    //Aqui é para excluir o pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!pedidoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pedidoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}