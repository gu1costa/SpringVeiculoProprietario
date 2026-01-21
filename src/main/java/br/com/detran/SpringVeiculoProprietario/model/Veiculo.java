package br.com.detran.SpringVeiculoProprietario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "veiculo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Placa é obrigatória.")
    @Pattern(
            regexp = "(?i)^(?:[A-Z]{3}\\d{4}|[A-Z]{3}\\d[A-Z]\\d{2})$",
            message = "Placa inválida. Use o formato ABC1234 ou ABC1D23."
    )
    @Column(nullable = false, length = 7, unique = true)
    private String placa;

    @NotBlank(message = "RENAVAM é obrigatório.")
    @Pattern(
            regexp = "^\\d{11}$",
            message = "RENAVAM inválido. Deve conter exatamente 11 dígitos numéricos."
    )
    @Column(nullable = false, length = 11, unique = true)
    private String renavam;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_prop", nullable = false)
    private Proprietario proprietario;

    @PrePersist
    @PreUpdate
    private void normalizarCampos() {
        if (placa != null) placa = placa.trim().toUpperCase();
        if (renavam != null) renavam = renavam.trim();
    }
}
