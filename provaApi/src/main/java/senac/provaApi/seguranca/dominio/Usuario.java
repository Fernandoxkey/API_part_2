package senac.provaApi.seguranca.dominio;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import senac.provaApi.seguranca.dominio.Papel;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false, unique = true)
    private String usuario;
    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    private Papel papel;

}
