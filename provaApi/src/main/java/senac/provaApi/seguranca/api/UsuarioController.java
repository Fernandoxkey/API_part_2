package senac.provaApi.seguranca.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import senac.provaApi.seguranca.dominio.Usuario;
import senac.provaApi.seguranca.dominio.UsuarioRepositorio;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @PostMapping("/registrar")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioCriado = usuarioRepositorio.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }
}
