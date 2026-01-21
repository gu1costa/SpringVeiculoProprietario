package br.com.detran.SpringVeiculoProprietario.service;

import br.com.detran.SpringVeiculoProprietario.exception.BusinessException;
import br.com.detran.SpringVeiculoProprietario.exception.ResourceNotFoundException;
import br.com.detran.SpringVeiculoProprietario.model.Proprietario;
import br.com.detran.SpringVeiculoProprietario.model.Veiculo;
import br.com.detran.SpringVeiculoProprietario.repository.ProprietarioRepository;
import br.com.detran.SpringVeiculoProprietario.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ProprietarioRepository proprietarioRepository;

    public VeiculoService(VeiculoRepository veiculoRepository, ProprietarioRepository proprietarioRepository) {
        this.veiculoRepository = veiculoRepository;
        this.proprietarioRepository = proprietarioRepository;
    }

    private String normalizarPlaca(String placa) {
        if (placa == null) return null;
        return placa.trim().toUpperCase();
    }

    private String normalizarRenavam(String renavam) {
        if (renavam == null) return null;
        return renavam.trim();
    }

    public List<Veiculo> getAll() {
        return veiculoRepository.findAll();
    }

    public Optional<Veiculo> getById(Long id) {
        return veiculoRepository.findById(id);
    }

    public Optional<Veiculo> getByPlaca(String placa) {
        return veiculoRepository.findByPlaca(normalizarPlaca(placa));
    }

    public Optional<Veiculo> getByRenavam(String renavam) {
        return veiculoRepository.findByRenavam(normalizarRenavam(renavam));
    }

    public List<Veiculo> listarPorProprietario(Long proprietarioId) {
        if(!proprietarioRepository.existsById(proprietarioId)){
            throw new ResourceNotFoundException("Proprietário não encontrado.");
        }
        return veiculoRepository.findByProprietarioId(proprietarioId);
    }

    public Veiculo create(Veiculo veiculo) {
        throw new BusinessException("Veículo precisa estar vinculado a um proprietário.");
    }

    public Veiculo createComProprietario(Long proprietarioId, Veiculo veiculo) {
        Proprietario proprietario = proprietarioRepository.findById(proprietarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Proprietário não encontrado."));

        veiculo.setPlaca(normalizarPlaca(veiculo.getPlaca()));
        veiculo.setRenavam(normalizarRenavam(veiculo.getRenavam()));

        if (veiculoRepository.findByPlaca(veiculo.getPlaca()).isPresent()) {
            throw new BusinessException("Placa já cadastrada.");
        }

        if (veiculoRepository.findByRenavam(veiculo.getRenavam()).isPresent()) {
            throw new BusinessException("RENAVAM já cadastrado.");
        }

        veiculo.setProprietario(proprietario);
        return veiculoRepository.save(veiculo);
    }

    public Veiculo update(Long id, Veiculo veiculo) {

        Veiculo existente = veiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado."));

        String novaPlaca = veiculo.getPlaca() != null ? veiculo.getPlaca().trim().toUpperCase() : null;
        String novoRenavam = veiculo.getRenavam() != null ? veiculo.getRenavam().trim() : null;

        if (novaPlaca != null && !novaPlaca.equals(existente.getPlaca())) {
            if (veiculoRepository.existsByPlacaAndIdNot(novaPlaca, id)) {
                throw new BusinessException("Placa já cadastrada.");
            }
            existente.setPlaca(novaPlaca);
        }

        if (novoRenavam != null && !novoRenavam.equals(existente.getRenavam())) {
            if (veiculoRepository.existsByRenavamAndIdNot(novoRenavam, id)) {
                throw new BusinessException("RENAVAM já cadastrado.");
            }
            existente.setRenavam(novoRenavam);
        }

        return veiculoRepository.save(existente);
    }

    public void delete(Long id) {
        Veiculo existente = veiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veículo não encontrado."));
        veiculoRepository.delete(existente);
    }
}
