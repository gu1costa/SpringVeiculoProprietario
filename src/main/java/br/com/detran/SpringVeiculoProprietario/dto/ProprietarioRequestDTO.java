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
public class ProprietarioRequestDTO {

    @NotBlank(message = "CPF/CNPJ é obrigatório.")
    @Pattern(
            regexp = "^\\d{11}$|^\\d{14}$",
            message = "CPF deve ter 11 dígitos e CNPJ 14 dígitos."
    )
    @Size(min = 11, max = 14, message = "CPF deve ter 11 dígitos e CNPJ 14 dígitos.")
    private String cpfCnpj;

    @NotBlank(message = "Nome é obrigatório.")
    private String nome;

    @NotBlank(message = "Endereço é obrigatório.")
    private String endereco;
}
