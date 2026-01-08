package br.com.detran.SpringVeiculoProprietario.service;

import br.com.detran.SpringVeiculoProprietario.model.Veiculo;
import br.com.detran.SpringVeiculoProprietario.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
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

        // Atualiza campos
        existente.setPlaca(veiculo.getPlaca());
        existente.setRenavam(veiculo.getRenavam());
        existente.setProprietario(veiculo.getProprietario());

        // Verificar duplicidade de placa se mudou
        if (!existente.getPlaca().equals(veiculo.getPlaca())) {
            if (veiculoRepository.findByPlaca(veiculo.getPlaca()).isPresent()) {
                throw new RuntimeException("Placa já cadastrada!");
            }
            existente.setPlaca(veiculo.getPlaca());
        }

        // Verificar duplicidade de RENAVAM se mudou
        if (!existente.getRenavam().equals(veiculo.getRenavam())) {
            if (veiculoRepository.findByRenavam(veiculo.getRenavam()).isPresent()) {
                throw new RuntimeException("RENAVAM já cadastrado!");
            }
            existente.setRenavam(veiculo.getRenavam());
        }

        return veiculoRepository.save(existente);
    }

    // Deletar veículo
    public void delete(Long id) {
        Veiculo existente = veiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado."));
        veiculoRepository.delete(existente);
    }
}