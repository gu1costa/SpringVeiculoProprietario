package br.com.detran.SpringVeiculoProprietario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProprietarioResponseDTO {
    private Long id;
    private String cpfCnpj;
    private String nome;
    private String endereco;
}
