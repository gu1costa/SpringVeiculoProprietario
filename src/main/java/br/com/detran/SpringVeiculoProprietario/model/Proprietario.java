package br.com.detran.SpringVeiculoProprietario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@Entity //JPA
@Table(name = "proprietario") //JPA
@Data //Lombok - Constrói os getter's e os setter's automaticamente.
@NoArgsConstructor //Lombok - Cria o construtor vazio.
@AllArgsConstructor //Lombok - Cria o contrutor cheio.
public class Proprietario {

    @Id                                                 //JPA
    @GeneratedValue(strategy = GenerationType.IDENTITY) //JPA
    private Long id;

    /*
    @Column(
            name = "nome_da_coluna",
            nullable = true | false,
            unique = true | false,
            length = número,
            updatable = true | false,
            insertable = true | false,
            columnDefinition = "SQL PURO"
    )
     */

    @NotBlank(message = "CPF/CNPJ é obrigatório.")
    @Pattern(
            regexp = "^\\d{11}$|^\\d{14}$",
            message = "CPF deve ter 11 dígitos e CNPJ 14 dígitos."
    )
    @Size(min = 11, max = 14, message = "CPF deve ter 11 dígitos e CNPJ 14 dígitos") // JPA
    @Column(name = "cpf_cnpj", length = 14, nullable = false, unique = true) //JPA
    private String cpfCnpj;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false)
    private String endereco;

    @OneToMany(mappedBy = "proprietario") // mappedBy = "proprietario" se refere ao atributo "private Proprietario proprietario" em Veiculo.java.
    @JsonManagedReference
    private List<Veiculo> veiculos = new ArrayList<>();
}
