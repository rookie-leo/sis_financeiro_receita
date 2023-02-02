package com.financeiro.receita.http.controllers;

import com.financeiro.receita.http.models.ReceitaRequest;
import com.financeiro.receita.http.models.ReceitaResponse;
import com.financeiro.receita.services.ReceitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<ReceitaResponse> cadastrar(@RequestBody @Valid ReceitaRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }

        var receitaResponse = service.cadastrar(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/receitas/{id}")
                .buildAndExpand(receitaResponse.getId())
                .toUri();

        return ResponseEntity.created(uri).body(receitaResponse);
    }

    @GetMapping()
    public List<ReceitaResponse> listar(@RequestParam(value = "descricao", required = false) String descricao) {
        if (descricao == null || descricao.isBlank()) {
            return service.listar();
        }

        return service.buscarPorDescricao(descricao);
    }

    @GetMapping("/{ano}/{mes}")
    public List<ReceitaResponse> listarPorMesEAno(@PathVariable String ano, @PathVariable String mes) {
        return service.buscarPorMesEAno(ano, mes);
    }

    @GetMapping("/{id}")
    public ReceitaResponse buscarReceita(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReceitaResponse> atualizarReceita(@PathVariable Long id,
                                                            @RequestBody @Valid ReceitaRequest request) {
        ReceitaResponse response = service.atualizar(id, request);

        return ResponseEntity.accepted().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarReceita(@PathVariable Long id) {
        service.deletar(id);

        return ResponseEntity.ok().build();
    }

}
