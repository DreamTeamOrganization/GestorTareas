package DreamTeam.GestorTareas.configs;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public Key jwtSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
