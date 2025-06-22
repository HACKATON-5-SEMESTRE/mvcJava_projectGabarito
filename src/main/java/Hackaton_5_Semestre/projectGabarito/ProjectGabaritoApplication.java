package Hackaton_5_Semestre.projectGabarito;

import Hackaton_5_Semestre.projectGabarito.model.Usuario;
import Hackaton_5_Semestre.projectGabarito.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
        boolean adminExists = usuarioRepository.findAll().stream()
                .anyMatch(user -> "ADMIN".equalsIgnoreCase(user.getRole()));

        if (!adminExists) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setLogin("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setCpf(0L);
            admin.setEmail("admin@exemplo.com");
            admin.setRole("ADMIN");
            usuarioRepository.save(admin);
            System.out.println("Admin criado com login 'admin' e senha 'admin'");
        }

        Usuario professor1 = usuarioRepository.findByLogin("prof1").orElse(null);
        if (professor1 == null) {
            professor1 = new Usuario();
            professor1.setNome("Professor Um");
            professor1.setLogin("prof1");
            professor1.setPassword(passwordEncoder.encode("123456"));
            professor1.setCpf(11111111111L);
            professor1.setEmail("prof1@escola.com");
            professor1.setRole("PROFESSOR");
            usuarioRepository.save(professor1);
            System.out.println("Professor 'prof1' criado com senha '123456'");
        }

        Usuario professor2 = usuarioRepository.findByLogin("prof2").orElse(null);
        if (professor2 == null) {
            professor2 = new Usuario();
            professor2.setNome("Professor Dois");
            professor2.setLogin("prof2");
            professor2.setPassword(passwordEncoder.encode("123456"));
            professor2.setCpf(22222222222L);
            professor2.setEmail("prof2@escola.com");
            professor2.setRole("PROFESSOR");
            usuarioRepository.save(professor2);
            System.out.println("Professor 'prof2' criado com senha '123456'");
        }
    }
}
