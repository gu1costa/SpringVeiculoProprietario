package br.com.detran.SpringVeiculoProprietario.service;

import br.com.detran.SpringVeiculoProprietario.model.Veiculo;
import br.com.detran.SpringVeiculoProprietario.repository.ProprietarioRepository;
import br.com.detran.SpringVeiculoProprietario.repository.VeiculoRepository;
import br.com.detran.SpringVeiculoProprietario.model.Proprietario;

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

    // Listar todos os veículos
    public List<Veiculo> getAll() {
        return veiculoRepository.findAll();
    }

    // Buscar veículo pelo ID
    public Optional<Veiculo> getById(Long id) {
        return veiculoRepository.findById(id);
    }

    // Buscar veículo pela placa
    public Optional<Veiculo> getByPlaca(String placa) {
        return veiculoRepository.findByPlaca(placa);
    }

    // Buscar veículo pelo RENAVAM
    public Optional<Veiculo> getByRenavam(String renavam) {
        return veiculoRepository.findByRenavam(renavam);
    }

    // Listar por Proprietário.
    public List<Veiculo> listarPorProprietario(Long proprietarioId) {
        return veiculoRepository.findByProprietarioId(proprietarioId);
    }

    // Criar novo veículo
    public Veiculo create(Veiculo veiculo) {
        // Validação de placa
        if (veiculoRepository.findByPlaca(veiculo.getPlaca()).isPresent()) {
            throw new RuntimeException("Placa já cadastrada!");
        }
        // Validação de RENAVAM
        if (veiculoRepository.findByRenavam(veiculo.getRenavam()).isPresent()) {
            throw new RuntimeException("RENAVAM já cadastrado!");
        }
        return veiculoRepository.save(veiculo);
    }

    // Atualizar veículo existente
    public Veiculo update(Long id, Veiculo veiculo) {

        Veiculo existente = veiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado."));

        if (!existente.getPlaca().equals(veiculo.getPlaca())) {
            if (veiculoRepository.findByPlaca(veiculo.getPlaca()).isPresent()) {
                throw new RuntimeException("Placa já cadastrada!");
            }
            existente.setPlaca(veiculo.getPlaca());
        }

        if (!existente.getRenavam().equals(veiculo.getRenavam())) {
            if (veiculoRepository.findByRenavam(veiculo.getRenavam()).isPresent()) {
                throw new RuntimeException("RENAVAM já cadastrado!");
            }
            existente.setRenavam(veiculo.getRenavam());
        }

        if (veiculo.getProprietario() != null) {
            existente.setProprietario(veiculo.getProprietario());
        }

        return veiculoRepository.save(existente);
    }

    // Deletar veículo
    public void delete(Long id) {
        Veiculo existente = veiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado."));
        veiculoRepository.delete(existente);
    }

    public Veiculo createComProprietario(Long proprietarioId, Veiculo veiculo) {

        Proprietario proprietario = proprietarioRepository.findById(proprietarioId)
                .orElseThrow(() -> new RuntimeException("Proprietário não encontrado"));

        if (veiculoRepository.findByPlaca(veiculo.getPlaca()).isPresent())
            throw new RuntimeException("Placa já cadastrada");

        if (veiculoRepository.findByRenavam(veiculo.getRenavam()).isPresent())
            throw new RuntimeException("RENAVAM já cadastrado");

        veiculo.setProprietario(proprietario);

        return veiculoRepository.save(veiculo);
    }

}