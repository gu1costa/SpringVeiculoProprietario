package br.com.detran.SpringVeiculoProprietario.repository;

import br.com.detran.SpringVeiculoProprietario.model.Proprietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long> {

    Optional<Proprietario> findByCpfCnpj(String cpfCnpj);

    boolean existsByCpfCnpj(String cpfCnpj);

    boolean existsByCpfCnpjAndIdNot(String cpfCnpj, Long id);
}

    /*
    O Optional é usado para representar o resultado de uma consulta que pode ou não existir, eliminando retornos null e
    forçando o tratamento consciente dos dois cenários: registro encontrado ou registro inexistente. Isso previne erros como
    NullPointerException e permite validar regras de negócio, como verificar se um CPF/CNPJ já está cadastrado ou ainda disponível.
     */

    /* Optional representa o resultado da busca por CPF/CNPJ: se existir,
    indica que o CPF já está cadastrado; se não existir, está disponível para cadastro.
    Não altera a obrigatoriedade do campo na entidade.
     */
