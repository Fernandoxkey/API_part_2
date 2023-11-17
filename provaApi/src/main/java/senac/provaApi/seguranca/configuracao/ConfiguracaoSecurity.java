package senac.provaApi.seguranca.configuracao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import senac.provaApi.seguranca.dominio.DefaultUserDetailsService;
import senac.provaApi.seguranca.dominio.UsuarioRepositorio;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
import static senac.provaApi.seguranca.dominio.Papel.*;

@Configuration
@EnableWebSecurity
public class ConfiguracaoSecurity {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);

    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepositorio usuarioRepositorio) throws Exception {
        //Define o objeto que sera utilizado para buscar o usuario durante a autenticacao
        return new DefaultUserDetailsService(usuarioRepositorio);
    }
    @Bean
    public SecurityFilterChain myFilterChain(HttpSecurity http) throws Exception {
        //Define as configuracoes de seguranca
        http
                //configuracao referente a Cross-site Request Forgery (CSRF)
                .csrf(AbstractHttpConfigurer::disable)
                //configuracao referente a Cross-Origin Resource Sharing (CORS)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((authz) -> authz
                        //permitAll() == acesso sem autenticacao
                        .requestMatchers(antMatcher("/publico/**")).permitAll()
                        //hasAnyRole(...) == autoriza apenas usuarios que tenham os papeis listados
                        .requestMatchers(antMatcher("/geral/**")).hasAnyRole(getNomesPapeis())
                        .requestMatchers(antMatcher("/restrito/**")).hasAnyRole(ADMIN.name(), GERENTE.name())
                        //hasRole(...) == autoriza apenas usuarios que tenham o unico papel especificado
                        .requestMatchers(antMatcher("/admin/**")).hasRole(ADMIN.name())
                        //requer autenticacao para qualquer url, exceto aquelas definidas como permitAll()
                        .anyRequest().authenticated()
                )
                //Desabilita o controle de secao (ou seja, cada requisicao deve ser autenticada novamente)
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}

