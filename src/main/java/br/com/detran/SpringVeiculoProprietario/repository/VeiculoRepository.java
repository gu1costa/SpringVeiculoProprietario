package br.com.detran.SpringVeiculoProprietario.repository;

import br.com.detran.SpringVeiculoProprietario.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    /*
     * Se já existir um veículo com essa placa, retorna que a placa já está cadastrada
     * e impede o cadastro de outro veículo com a mesma placa.
     *
     * Se já existir um veículo com esse RENAVAM, retorna que o RENAVAM já está cadastrado
     * e impede o cadastro de outro veículo com o mesmo RENAVAM.
     *
     * Se nenhum dos dois existir, o veículo pode ser cadastrado normalmente.
     */

    Optional<Veiculo> findByPlaca(String placa);
    Optional<Veiculo> findByRenavam(String renavam);

    // Listar veículos por proprietário.
    List<Veiculo> findByProprietarioId(Long proprietarioId);
}
