package utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;


public class JwtUtil {
    private static final String SECRET = "ConnectWorkClaveSuperSegura";
    private static final Algorithm ALGORITHM =
            Algorithm.HMAC256(SECRET);

    private static final long EXPIRACION =
            1000 * 60 * 60 * 24; // 24 horas

    public static String generarToken(int idUsuario, String username, String rol) {

        return JWT.create()
                .withSubject("ConnectWork")
                .withClaim("id", idUsuario)
                .withClaim("username", username)
                .withClaim("rol", rol)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRACION))
                .sign(ALGORITHM);
    }

    public static DecodedJWT validarToken(String token) {

        return JWT.require(ALGORITHM)
                .build()
                .verify(token);
    }

    public static int obtenerId(String token) {
        return validarToken(token)
                .getClaim("id")
                .asInt();
    }

    public static String obtenerRol(String token) {
        return validarToken(token)
                .getClaim("rol")
                .asString();
    }

    public static String obtenerUsername(String token) {
        return validarToken(token)
                .getClaim("username")
                .asString();
    }
}
