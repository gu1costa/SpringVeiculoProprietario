package br.com.detran.SpringVeiculoProprietario.repository;

import br.com.detran.SpringVeiculoProprietario.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    Optional<Veiculo> findByPlaca(String placa);

    Optional<Veiculo> findByRenavam(String renavam);

    boolean existsByRenavamAndIdNot(String renavam, Long id);

    boolean existsByPlacaAndIdNot(String placa, Long id);

    List<Veiculo> findByProprietarioId(Long proprietarioId);
}
