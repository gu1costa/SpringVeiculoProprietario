package br.com.detran.SpringVeiculoProprietario.controller;

import br.com.detran.SpringVeiculoProprietario.model.Veiculo;
import br.com.detran.SpringVeiculoProprietario.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @GetMapping
    public ResponseEntity<List<Veiculo>> findAll() {
        return ResponseEntity.ok(veiculoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> getById(@PathVariable Long id) {
        return veiculoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<Veiculo> getByPlaca(@PathVariable String placa) {
        return veiculoService.getByPlaca(placa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/renavam/{renavam}")
    public ResponseEntity<Veiculo> getByRenavam(@PathVariable String renavam) {
        return veiculoService.getByRenavam(renavam)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/proprietario/{proprietarioId}")
    public ResponseEntity<List<Veiculo>> listarPorProprietario(@PathVariable Long proprietarioId) {
        return ResponseEntity.ok(veiculoService.listarPorProprietario(proprietarioId));
    }

    @PostMapping("/proprietario/{proprietarioId}")
    public ResponseEntity<Veiculo> createComProprietario(
            @PathVariable Long proprietarioId,
            @RequestBody @Valid Veiculo veiculo
    ) {
        Veiculo criado = veiculoService.createComProprietario(proprietarioId, veiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Veiculo> update(
            @PathVariable Long id,
            @RequestBody @Valid Veiculo veiculo
    ) {
        Veiculo atualizado = veiculoService.update(id, veiculo);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        veiculoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
