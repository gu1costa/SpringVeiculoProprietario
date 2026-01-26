package br.com.detran.SpringVeiculoProprietario.controller;

import br.com.detran.SpringVeiculoProprietario.dto.VeiculoRequestDTO;
import br.com.detran.SpringVeiculoProprietario.dto.VeiculoResponseDTO;
import br.com.detran.SpringVeiculoProprietario.mapper.VeiculoMapper;
import br.com.detran.SpringVeiculoProprietario.model.Veiculo;
import br.com.detran.SpringVeiculoProprietario.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    public VeiculoController(VeiculoService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponseDTO>> findAll() {
        List<VeiculoResponseDTO> lista = veiculoService.getAll().stream()
                .map(VeiculoMapper::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponseDTO> getById(@PathVariable Long id) {
        return veiculoService.getById(id)
                .map(VeiculoMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/placa/{placa}")
    public ResponseEntity<VeiculoResponseDTO> getByPlaca(@PathVariable String placa) {
        return veiculoService.getByPlaca(placa)
                .map(VeiculoMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/renavam/{renavam}")
    public ResponseEntity<VeiculoResponseDTO> getByRenavam(@PathVariable String renavam) {
        return veiculoService.getByRenavam(renavam)
                .map(VeiculoMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/proprietario/{proprietarioId}")
    public ResponseEntity<List<VeiculoResponseDTO>> listarPorProprietario(@PathVariable Long proprietarioId) {
        List<VeiculoResponseDTO> lista = veiculoService.listarPorProprietario(proprietarioId).stream()
                .map(VeiculoMapper::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PostMapping("/proprietario/{proprietarioId}")
    public ResponseEntity<VeiculoResponseDTO> createComProprietario(
            @PathVariable Long proprietarioId,
            @RequestBody @Valid VeiculoRequestDTO veiculoDto
    ) {
        Veiculo veiculo = VeiculoMapper.toEntity(veiculoDto);

        Veiculo criado = veiculoService.createComProprietario(proprietarioId, veiculo);

        VeiculoResponseDTO response = VeiculoMapper.toResponseDTO(criado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<VeiculoResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid VeiculoRequestDTO veiculoDto
    ) {
        Veiculo veiculo = VeiculoMapper.toEntity(veiculoDto);

        Veiculo atualizado = veiculoService.update(id, veiculo);

        VeiculoResponseDTO response = VeiculoMapper.toResponseDTO(atualizado);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        veiculoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
