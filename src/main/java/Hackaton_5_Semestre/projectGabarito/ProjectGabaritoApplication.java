package Hackaton_5_Semestre.projectGabarito;

import Hackaton_5_Semestre.projectGabarito.model.Aluno;
import Hackaton_5_Semestre.projectGabarito.model.Turma;
import Hackaton_5_Semestre.projectGabarito.model.Usuario;
import Hackaton_5_Semestre.projectGabarito.repository.UsuarioRepository;
import Hackaton_5_Semestre.projectGabarito.service.AlunoService;
import Hackaton_5_Semestre.projectGabarito.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProjectGabaritoApplication implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ProjectGabaritoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        boolean adminExists = usuarioRepository.findAll().stream().anyMatch(user -> "ADMIN".equalsIgnoreCase(user.getRole()));

        if (!adminExists) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setLogin("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setCpf(0L);
            admin.setEmail("admin@exemplo.com");
            admin.setRole("ADMIN");
            usuarioRepository.save(admin);
            System.out.println("Admin criado com login 'admin' e senha 'admin123'");
        }
    }

    @Bean
    public CommandLineRunner popularDados(AlunoService alunoService, TurmaService turmaService) {
        return args -> {
            if (alunoService.listarTodos().isEmpty()) {
                Turma turma = new Turma(null, "Sistemas de Informação", "5º Semestre", "A");
                turmaService.salvar(turma);

                alunoService.salvar(
                        new Aluno(
                                "João da Silva",
                                "joaos",
                                "123",
                                12345678900L,
                                "joao@gmail.com",
                                "USER", "1001",
                                turma));
                alunoService.salvar(
                        new Aluno(
                                "Maria Oliveira",
                                "mariao", "123",
                                12345678901L,
                                "maria@gmail.com",
                                "USER", "1002",
                                turma));
                alunoService.salvar(
                        new Aluno(
                                "Carlos Lima",
                                "carlim",
                                "123",
                                12345678902L,
                                "carlos@gmail.com",
                                "USER",
                                "1003",
                                turma));

                System.out.println("✔ Alunos de teste criados.");
            }
        };
    }
}
