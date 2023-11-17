package senac.provaApi.seguranca.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import senac.provaApi.seguranca.api.dtos.RegistrarUsuarioRequisicao;
import senac.provaApi.seguranca.dominio.Papel;
import senac.provaApi.seguranca.dominio.Usuario;
import senac.provaApi.seguranca.dominio.UsuarioRepositorio;

@RestController
@RequestMapping("/publico/usuarios")
@AllArgsConstructor
public class RegistroUsuarioControlador {

    private UsuarioRepositorio usuarioRepositorio;
    private PasswordEncoder codificadorDeSenhas;

    @PostMapping
    public ResponseEntity registrar(@RequestBody @Valid RegistrarUsuarioRequisicao requisicao) {

        if (requisicao.senhasNaoConferem()) {
            return ResponseEntity.badRequest().body("Senha diferente da confirmação");
        }

        if (usuarioRepositorio.findByUsuario(requisicao.getUsuario()).isPresent()) {
            return ResponseEntity.badRequest().body("Nome de usuário indisponível!");
        }

        var usuarioSalvo = usuarioRepositorio.save(Usuario
                .builder()
                .usuario(requisicao.getUsuario())
                .senha(codificadorDeSenhas.encode(requisicao.getSenha()))
                .papel(Papel.USUARIO)
                .build());

        if (usuarioSalvo != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.internalServerError().build();
    }
}
