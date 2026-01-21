package br.com.detran.SpringVeiculoProprietario.service;

import br.com.detran.SpringVeiculoProprietario.exception.BusinessException;
import br.com.detran.SpringVeiculoProprietario.exception.ResourceNotFoundException;
import br.com.detran.SpringVeiculoProprietario.model.Proprietario;
import br.com.detran.SpringVeiculoProprietario.repository.ProprietarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProprietarioService {

    private final ProprietarioRepository proprietarioRepository;

    public ProprietarioService(ProprietarioRepository proprietarioRepository) {
        this.proprietarioRepository = proprietarioRepository;
    }

    private String normalizarCpfCnpj(String cpfCnpj) {
        if (cpfCnpj == null) return null;
        return cpfCnpj.trim();
    }

    public List<Proprietario> getAll() {
        return proprietarioRepository.findAll();
    }

    public Optional<Proprietario> getById(Long id) {
        return proprietarioRepository.findById(id);
    }

    public Optional<Proprietario> getByCpfCnpj(String cpfCnpj) {
        return proprietarioRepository.findByCpfCnpj(normalizarCpfCnpj(cpfCnpj));
    }

    public Proprietario create(Proprietario proprietario) {
        String cpfCnpj = normalizarCpfCnpj(proprietario.getCpfCnpj());
        proprietario.setCpfCnpj(cpfCnpj);

        if (proprietarioRepository.existsByCpfCnpj(cpfCnpj)) {
            throw new BusinessException("CPF/CNPJ já cadastrado.");
        }

        return proprietarioRepository.save(proprietario);
    }

    public Proprietario update(Long id, Proprietario proprietario) {
        Proprietario existente = proprietarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proprietário não encontrado."));

        String novoCpfCnpj = normalizarCpfCnpj(proprietario.getCpfCnpj());

        if (novoCpfCnpj != null && !novoCpfCnpj.equals(existente.getCpfCnpj())) {
            if (proprietarioRepository.existsByCpfCnpjAndIdNot(novoCpfCnpj, id)) {
                throw new BusinessException("CPF/CNPJ já cadastrado.");
            }
            existente.setCpfCnpj(novoCpfCnpj);
        }

        existente.setNome(proprietario.getNome());
        existente.setEndereco(proprietario.getEndereco());

        return proprietarioRepository.save(existente);
    }

    public void delete(Long id) {
        Proprietario existente = proprietarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proprietário não encontrado."));
        proprietarioRepository.delete(existente);
    }
}
