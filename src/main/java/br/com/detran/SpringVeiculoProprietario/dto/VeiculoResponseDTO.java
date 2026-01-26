package br.com.detran.SpringVeiculoProprietario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VeiculoResponseDTO {

    private Long id;
    private String placa;
    private String renavam;
    private Long proprietarioId;
}
