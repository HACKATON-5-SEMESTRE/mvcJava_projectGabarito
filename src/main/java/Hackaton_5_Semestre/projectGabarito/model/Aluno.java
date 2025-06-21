package Hackaton_5_Semestre.projectGabarito.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Aluno extends Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "turma_id", nullable = true)
    private Turma turma;

    private String RA;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Aluno(String nome, String login, String password, Long cpf, String email, String role, String RA, Turma turma) {
        super(null, nome, login, password, cpf, email, role);
        this.RA = RA;
        this.turma = turma;
    }
}
