package br.com.detran.SpringVeiculoProprietario.service;

import br.com.detran.SpringVeiculoProprietario.exception.DuplicateCpfCnpjException;
import br.com.detran.SpringVeiculoProprietario.model.Proprietario;
import br.com.detran.SpringVeiculoProprietario.repository.ProprietarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class ProprietarioService {

    //Métodos para listar os proprietários:

    //Injeção de dependência.
    private final ProprietarioRepository proprietarioRepository;

    public ProprietarioService(ProprietarioRepository proprietarioRepository) {
        this.proprietarioRepository = proprietarioRepository;
    }

    //Mostra todos os proprietários.
    public List<Proprietario> getAll() {
        return proprietarioRepository.findAll();
    }

    //Buscar o proprietário pelo ID.
    public Optional<Proprietario> getById(Long id) {
        return proprietarioRepository.findById(id);
    }

    //Buscar o proprietário pelo CPF ou CNPJ.
    public Optional<Proprietario> getByCpfCnpj(String cpfCnpj) {
        return proprietarioRepository.findByCpfCnpj(cpfCnpj);
    }

    /// Criar um novo proprietário.
    public Proprietario create(Proprietario proprietario){
        //Verificação se já existe um proprietário com o mesmo CPF/CNPJ.
        if(proprietarioRepository.findByCpfCnpj(proprietario.getCpfCnpj()).isPresent()){ //Se um proprietarioRepository.findByCpfCpnj(verificação se o CPF/CNPJ já está presente for verdadeiro): lança uma exceção RuntimeException com aquela mensagem.
            throw new DuplicateCpfCnpjException("CPF/CNPJ já cadastrado!");
        }
        return proprietarioRepository.save(proprietario); //e o CPF/CNPJ não existir, o método chama save(proprietario) do JpaRepository, que: Insere o Proprietário no banco de dados (INSERT). Gera automaticamente o id do Proprietário (porque usamos @GeneratedValue).
    }

    //Atualizar o proprietário.
    public Proprietario update(Long id, Proprietario proprietario){
        Proprietario existente =  proprietarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Proprietário não encontrado."));

        //Atualiza os campos.
        existente.setNome(proprietario.getNome());
        existente.setEndereco(proprietario.getEndereco());

        // Se o CPF/CNPJ for diferente, valida duplicidade.
        if (!existente.getCpfCnpj().equals(proprietario.getCpfCnpj())) {
            if (proprietarioRepository.findByCpfCnpj(proprietario.getCpfCnpj()).isPresent()) {
                throw new RuntimeException("CPF/CNPJ já cadastrado!");
            }
            existente.setCpfCnpj(proprietario.getCpfCnpj());
        }
        return proprietarioRepository.save(existente);
    }

    //Deletar o proprietário.
    public void delete(Long id){
        Proprietario existente = proprietarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Proprietário não encontrado."));
        proprietarioRepository.delete(existente);
    }
}
