package br.com.detran.SpringVeiculoProprietario.model;

import jakarta.persistence.*;
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

    @Column(nullable = false, length = 7, unique = true)
    private String placa;

    @Column(nullable = false, length = 11, unique = true)
    private String renavam;

    @ManyToOne
    @JoinColumn(name = "id_prop", nullable = false)
    private Proprietario proprietario;
}
