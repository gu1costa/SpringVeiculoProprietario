package br.com.detran.SpringVeiculoProprietario.mapper;

import br.com.detran.SpringVeiculoProprietario.dto.ProprietarioRequestDTO;
import br.com.detran.SpringVeiculoProprietario.dto.ProprietarioResponseDTO;
import br.com.detran.SpringVeiculoProprietario.model.Proprietario;

public class ProprietarioMapper {
    public static Proprietario toEntity(ProprietarioRequestDTO dto){
        Proprietario proprietario = new Proprietario();

        proprietario.setCpfCnpj(dto.getCpfCnpj());
        proprietario.setNome(dto.getNome());
        proprietario.setEndereco(dto.getEndereco());

        return proprietario;
    }

    public static ProprietarioResponseDTO toResponseDTO(Proprietario entity){
        return new ProprietarioResponseDTO(
                entity.getId(),
                entity.getCpfCnpj(),
                entity.getNome(),
                entity.getEndereco()
        );
    }
}
