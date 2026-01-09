package br.com.detran.SpringVeiculoProprietario.controller;

import br.com.detran.SpringVeiculoProprietario.model.Veiculo;
import br.com.detran.SpringVeiculoProprietario.service.VeiculoService;
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
    // Retorna todos os veículos
    public ResponseEntity<List<Veiculo>> findAll() {
        return ResponseEntity.ok(veiculoService.getAll());
    }

    @GetMapping("/{id}")
    // Busca veículo pelo ID
    public ResponseEntity<Veiculo> getById(@PathVariable Long id) {
        return veiculoService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/placa/{placa}")
    // Busca veículo pela placa
    public ResponseEntity<Veiculo> getByPlaca(@PathVariable String placa) {
        return veiculoService.getByPlaca(placa)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/renavam/{renavam}")
    // Busca veículo pelo RENAVAM
    public ResponseEntity<Veiculo> getByRenavam(@PathVariable String renavam) {
        return veiculoService.getByRenavam(renavam)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/criar")
    // Cria novo veículo
    public ResponseEntity<Veiculo> create(@RequestBody Veiculo veiculo) { //@RequestBody pede para mandar no corpo da requisição os atributos de Veiculo.
        Veiculo criado = veiculoService.create(veiculo);
        return ResponseEntity.ok(criado);

    }

    @PutMapping("/atualizar/{id}")
    // Atualiza veículo existente
    public ResponseEntity<Veiculo> update(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        Veiculo atualizado = veiculoService.update(id, veiculo);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/deletar/{id}")
    // Deleta veículo pelo ID
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        veiculoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/proprietario/{proprietarioId}")
    public ResponseEntity<Veiculo> createComProprietario(@PathVariable Long proprietarioId,
                                                         @RequestBody Veiculo veiculo) {
        Veiculo criado = veiculoService.createComProprietario(proprietarioId, veiculo);
        return ResponseEntity.ok(criado);
    }
}