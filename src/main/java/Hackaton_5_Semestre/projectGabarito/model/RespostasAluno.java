package Hackaton_5_Semestre.projectGabarito.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RespostasAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_aluno", nullable = false)
    private Aluno aluno;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String respostas;

    @Column(precision = 5, scale = 2)
    private BigDecimal nota;

    private Integer acertos;
    private Integer erros;

    @Column(name = "data_envio", nullable = false)
    private LocalDateTime dataEnvio;
}
