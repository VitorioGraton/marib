package br.com.fiap.marys_pizza.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.fiap.marys_pizza.models.Credencial;
import br.com.fiap.marys_pizza.models.TokenJwt;

@Service
public class TokenService {

    public TokenJwt generateToken(Credencial credencial) {
        Algorithm alg = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withSubject(credencial.email())
                .withIssuer("MarysPizza")
                .withExpiresAt(Instant.now().plus(2, ChronoUnit.HOURS))
                .sign(alg);
        return new TokenJwt(token);
    }

    public String valide(String token) {
        Algorithm alg = Algorithm.HMAC256("secret");
        return JWT.require(alg)
                .withIssuer("MarysPizza")
                .build()
                .verify(token)
                .getSubject();
    }

}
