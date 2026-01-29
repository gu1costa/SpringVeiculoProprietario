package br.com.detran.SpringVeiculoProprietario.mapper;

import br.com.detran.SpringVeiculoProprietario.dto.ProprietarioRequestDTO;
import br.com.detran.SpringVeiculoProprietario.dto.ProprietarioResponseDTO;
import br.com.detran.SpringVeiculoProprietario.dto.ProprietarioUpdateDTO;
import br.com.detran.SpringVeiculoProprietario.exception.BusinessException;
import br.com.detran.SpringVeiculoProprietario.model.Proprietario;
import org.apache.commons.lang3.StringUtils;

public class ProprietarioMapper {

    public static Proprietario toEntity(ProprietarioRequestDTO dto) {
        Proprietario p = new Proprietario();
        p.setCpfCnpj(dto.getCpfCnpj());
        p.setNome(dto.getNome());
        p.setEndereco(dto.getEndereco());
        return p;
    }

    public static void applyUpdates(Proprietario existente, ProprietarioUpdateDTO dto) {
        boolean mudou = false;

        if (StringUtils.isNotBlank(dto.getNome())) {
            existente.setNome(dto.getNome());
            mudou = true;
        }

        if (StringUtils.isNotBlank(dto.getEndereco())) {
            existente.setEndereco(dto.getEndereco());
            mudou = true;
        }

        if (!mudou) {
            throw new BusinessException("Envie pelo menos um campo para atualizar: NOME ou ENDERECO.");
        }
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
