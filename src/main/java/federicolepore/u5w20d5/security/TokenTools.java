package federicolepore.u5w20d5.security;

import federicolepore.u5w20d5.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class TokenTools {

    private final String secret;
    private int tokenDuration = 1000 * 60 * 60 * 24 * 7; // una settimana

    public TokenTools(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }


    public String generateToken(User user) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenDuration))
                .subject(String.valueOf(user.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }


    public void verificationToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex) {
            throw new RuntimeException();  //TODO sostituire con personalizzata
        }
    }


    public UUID extractFromToken(String token) {
        return UUID.fromString(Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject());
    }

}
