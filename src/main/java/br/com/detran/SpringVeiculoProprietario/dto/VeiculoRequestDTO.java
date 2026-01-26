package br.com.detran.SpringVeiculoProprietario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VeiculoRequestDTO {

    @NotBlank(message = "Placa é obrigatória.")
    @Pattern(
            regexp = "(?i)^(?:[A-Z]{3}\\d{4}|[A-Z]{3}\\d[A-Z]\\d{2})$",
            message = "Placa inválida. Use o formato ABC1234 ou ABC1D23."
    )
    private String placa;

    @NotBlank(message = "Renavam é obrigatório.")
    @Pattern(
            regexp = "^\\d{11}$",
            message = "RENAVAM inválido. Deve conter exatamente 11 dígitos numéricos."
    )
    private String renavam;
}
