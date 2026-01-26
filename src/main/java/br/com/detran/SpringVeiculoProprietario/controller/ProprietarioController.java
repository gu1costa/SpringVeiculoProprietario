package br.com.detran.SpringVeiculoProprietario.controller;

import br.com.detran.SpringVeiculoProprietario.dto.ProprietarioRequestDTO;
import br.com.detran.SpringVeiculoProprietario.dto.ProprietarioResponseDTO;
import br.com.detran.SpringVeiculoProprietario.mapper.ProprietarioMapper;
import br.com.detran.SpringVeiculoProprietario.model.Proprietario;
import br.com.detran.SpringVeiculoProprietario.service.ProprietarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {

    private final ProprietarioService proprietarioService;

    public ProprietarioController(ProprietarioService proprietarioService) {
        this.proprietarioService = proprietarioService;
    }

    @GetMapping
    public ResponseEntity<List<ProprietarioResponseDTO>> findAll() {
        List<ProprietarioResponseDTO> lista = proprietarioService.getAll().stream()
                .map(ProprietarioMapper::toResponseDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProprietarioResponseDTO> getById(@PathVariable Long id) {
        return proprietarioService.getById(id)
                .map(ProprietarioMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpfCnpj}")
    public ResponseEntity<ProprietarioResponseDTO> getByCpfCnpj(
            @PathVariable
            @Pattern(regexp = "\\d{11}|\\d{14}", message = "CPF deve ter 11 dígitos e CNPJ 14 dígitos")
            String cpfCnpj
    ) {
        return proprietarioService.getByCpfCnpj(cpfCnpj)
                .map(ProprietarioMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<ProprietarioResponseDTO> create(@RequestBody @Valid ProprietarioRequestDTO proprietarioDto) {
        Proprietario proprietario = ProprietarioMapper.toEntity(proprietarioDto);

        Proprietario criado = proprietarioService.create(proprietario);

        ProprietarioResponseDTO response = ProprietarioMapper.toResponseDTO(criado);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ProprietarioResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid ProprietarioRequestDTO proprietarioDto
    ) {
        Proprietario proprietario = ProprietarioMapper.toEntity(proprietarioDto);

        Proprietario atualizado = proprietarioService.update(id, proprietario);

        ProprietarioResponseDTO response = ProprietarioMapper.toResponseDTO(atualizado);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        proprietarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
