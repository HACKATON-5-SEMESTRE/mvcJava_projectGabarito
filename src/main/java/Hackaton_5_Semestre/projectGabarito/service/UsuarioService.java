package Hackaton_5_Semestre.projectGabarito.service;

import Hackaton_5_Semestre.projectGabarito.model.Usuario;
import Hackaton_5_Semestre.projectGabarito.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public boolean loginJaExisteParaOutroUsuario(String login, Long idAtual) {
        return repository.findByLogin(login).stream()
                .anyMatch(usuario -> !usuario.getId().equals(idAtual));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    public boolean loginExiste(String login, Long idIgnorar) {
        Optional<Usuario> existente = repository.findByLogin(login);
        return existente.isPresent() && !existente.get().getId().equals(idIgnorar);
    }

    public boolean emailExiste(String email, Long idIgnorar) {
        Optional<Usuario> existente = repository.findByEmailIgnoreCase(email);
        return existente.isPresent() && !existente.get().getId().equals(idIgnorar);
    }

    public List<Usuario> listAll() {
        return repository.findAll();
    }

    public Usuario salvar(Usuario usuario) {
        return repository.save(usuario);
    }
}
