package br.com.detran.SpringVeiculoProprietario.mapper;

import br.com.detran.SpringVeiculoProprietario.dto.ProprietarioRequestDTO;
import br.com.detran.SpringVeiculoProprietario.dto.ProprietarioResponseDTO;
import br.com.detran.SpringVeiculoProprietario.exception.BusinessException;
import br.com.detran.SpringVeiculoProprietario.model.Proprietario;
import org.apache.commons.lang3.StringUtils;

public class ProprietarioMapper {
    public static Proprietario toEntity(ProprietarioRequestDTO dto) {
        Proprietario proprietario = new Proprietario();

//        proprietario.setCpfCnpj(dto.getCpfCnpj());

        if (StringUtils.isNotBlank(dto.getEndereco())) {
            proprietario.setEndereco(dto.getEndereco());
        }

        if (StringUtils.isNotBlank(dto.getNome())) {
            proprietario.setNome(dto.getNome());
        }

        if (StringUtils.isBlank(dto.getEndereco()) && StringUtils.isBlank(dto.getNome())) {
            throw new BusinessException("Pelo menos um dos campos precisa ser enviado 'ENDERECO' ou 'NOME'.");
        }

        return proprietario;
    }

    public static ProprietarioResponseDTO toResponseDTO(Proprietario entity) {
        return new ProprietarioResponseDTO(
                entity.getId(),
                entity.getCpfCnpj(),
                entity.getNome(),
                entity.getEndereco()
        );
    }
}
