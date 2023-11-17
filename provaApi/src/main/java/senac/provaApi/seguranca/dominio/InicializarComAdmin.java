package senac.provaApi.seguranca.dominio;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InicializarComAdmin implements ApplicationListener<ApplicationReadyEvent> {
    private UsuarioRepositorio usuarioRepositorio;
    private PasswordEncoder codificadorDeSenhas;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (usuarioRepositorio.searchByPapel(Papel.ADMIN).isEmpty()) {
            //cria um ADMIN padrão se não encontrar nenhum ADMIN
            var usuarioSalvo = usuarioRepositorio.save(Usuario
                    .builder()
                    .usuario("admin")
                    .senha(codificadorDeSenhas.encode("mestre"))
                    .papel(Papel.ADMIN)
                    .build());
        }
    }
}
