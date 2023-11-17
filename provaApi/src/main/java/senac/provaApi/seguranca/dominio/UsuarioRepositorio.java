package senac.provaApi.seguranca.dominio;

import org.springframework.data.jpa.repository.JpaRepository;
import senac.provaApi.seguranca.dominio.Papel;
import senac.provaApi.seguranca.dominio.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuario(String usuario);
    List<Usuario>searchByPapel(Papel papel);
}
