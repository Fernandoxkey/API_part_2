package senac.provaApi.seguranca.dominio;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@AllArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username != null && !username.isBlank()) {
            var usuario = usuarioRepositorio.findByUsuario(username).orElseThrow(() -> new UsernameNotFoundException("Not Found"));
            return User.builder()
                    .username(usuario.getUsuario())
                    .password(usuario.getSenha())
                    .roles(usuario.getPapel().name())
                    .build();
        }

        throw new UsernameNotFoundException("Not Found");
    }
}
