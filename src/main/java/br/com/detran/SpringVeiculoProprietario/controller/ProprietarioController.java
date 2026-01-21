package br.com.detran.SpringVeiculoProprietario.controller;

import br.com.detran.SpringVeiculoProprietario.model.Proprietario;
import br.com.detran.SpringVeiculoProprietario.service.ProprietarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {

    private final ProprietarioService proprietarioService;

    public ProprietarioController(ProprietarioService proprietarioService) {
        this.proprietarioService = proprietarioService;
    }

    @GetMapping
    public ResponseEntity<List<Proprietario>> findAll() {
        return ResponseEntity.ok(proprietarioService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proprietario> getById(@PathVariable Long id) {
        return proprietarioService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpfCnpj}")
    public ResponseEntity<Proprietario> getByCpfCnpj(
            @PathVariable
            @Pattern(regexp = "\\d{11}|\\d{14}", message = "CPF deve ter 11 dígitos e CNPJ 14 dígitos")
            String cpfCnpj
    ) {
        return proprietarioService.getByCpfCnpj(cpfCnpj)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Proprietario> create(@RequestBody @Valid Proprietario proprietario) {
        Proprietario criado = proprietarioService.create(proprietario);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Proprietario> update(
            @PathVariable Long id,
            @RequestBody @Valid Proprietario proprietario
    ) {
        Proprietario atualizado = proprietarioService.update(id, proprietario);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        proprietarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
