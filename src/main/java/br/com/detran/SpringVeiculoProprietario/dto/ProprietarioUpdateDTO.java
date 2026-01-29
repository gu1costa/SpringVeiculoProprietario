package br.com.detran.SpringVeiculoProprietario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProprietarioUpdateDTO {
    private String nome;
    private String endereco;
}
