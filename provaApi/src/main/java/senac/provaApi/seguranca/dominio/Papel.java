package senac.provaApi.seguranca.dominio;

import java.util.Arrays;

public enum Papel {
    ADMIN,
    GERENTE,
    USUARIO;

    public static String[] getNomesPapeis() {
        return Arrays.stream(Papel.values())
                .map(Papel::name)
                .toArray(String[]::new);
    }
}
