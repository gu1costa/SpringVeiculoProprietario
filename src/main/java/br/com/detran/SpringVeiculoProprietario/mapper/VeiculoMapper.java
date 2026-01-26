package br.com.detran.SpringVeiculoProprietario.mapper;

import br.com.detran.SpringVeiculoProprietario.dto.VeiculoRequestDTO;
import br.com.detran.SpringVeiculoProprietario.dto.VeiculoResponseDTO;
import br.com.detran.SpringVeiculoProprietario.model.Veiculo;

public class VeiculoMapper {
    public static Veiculo toEntity(VeiculoRequestDTO dto){
        Veiculo veiculo = new Veiculo();

        veiculo.setPlaca(dto.getPlaca());
        veiculo.setRenavam(dto.getRenavam());

        return veiculo;
    }

    public static VeiculoResponseDTO toResponseDTO(Veiculo entity){
        Long proprietarioId = null;

        if(entity.getProprietario() != null){
            proprietarioId = entity.getProprietario().getId();
        }

        return new VeiculoResponseDTO(
                entity.getId(),
                entity.getPlaca(),
                entity.getRenavam(),
                proprietarioId
        );
    }
}
