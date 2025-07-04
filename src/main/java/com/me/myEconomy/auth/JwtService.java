package com.me.myEconomy.auth;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.me.myEconomy.model.entity.Usuario;


@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(Authentication subject) {

        Instant now = Instant.now();

        long tenHoursInSeconds = 36000L;

        String roles = subject
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Usuario authenticatedUser = (Usuario) subject.getPrincipal();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("gs")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(tenHoursInSeconds))
                .subject(subject.getName())
                .claim("roles", roles)
                .claim("idUsuario", authenticatedUser.getIdUsuario())
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(claims)).getTokenValue();
    }
}