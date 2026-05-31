package com.agpf.workhub.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class JwtService {

    private final byte[] secret;
    private final long expirationSeconds;
    private final ObjectMapper objectMapper;
    private static final String HMAC_ALGORITHM = "HmacSHA256";

    public JwtService(@Value("${auth.jwt.secret}") String secret, @Value("${auth.jwt.expiration-minutes}") long expirationMinutes) {
        this.objectMapper = new ObjectMapper();
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
        this.expirationSeconds = expirationMinutes * 60;
    }

    public String generateToken(String email, String role) {
        var now = Instant.now();
        var header = new LinkedHashMap<String, Object>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        var claims = new LinkedHashMap<String, Object>();
        claims.put("sub", email);
        claims.put("role", role);
        claims.put("iat", now.getEpochSecond());
        claims.put("exp", now.plusSeconds(this.expirationSeconds).getEpochSecond());

        var unsignedToken = encodeJson(header) + "." + encodeJson(claims);
        return unsignedToken + "." + sign(unsignedToken);
    }

    public String extractSubject(String token) {
        return claims(token).get("sub").toString();
    }

    public boolean isValid(String token, UserDetails userDetails) {
        if (!StringUtils.hasText(token) || !hasValidSignature(token))
            return false;

        var claims = claims(token);
        var subject = claims.get("sub");
        var expiration = claims.get("exp");
        return userDetails.getUsername().equals(subject) && expiration instanceof Number exp && exp.longValue() > Instant.now().getEpochSecond();
    }

    private boolean hasValidSignature(String token) {
        var parts = token.split("\\.");
        if (parts.length != 3)
            return false;

        var unsignedToken = parts[0] + "." + parts[1];
        return MessageDigest.isEqual(sign(unsignedToken).getBytes(StandardCharsets.UTF_8), parts[2].getBytes(StandardCharsets.UTF_8));
    }

    private Map<String, Object> claims(String token) {
        try {
            var parts = token.split("\\.");
            var payload = Base64.getUrlDecoder().decode(parts[1]);
            return this.objectMapper.readValue(payload, new TypeReference<>() {
            });
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid JWT", ex);
        }
    }

    private String encodeJson(Map<String, Object> value) {
        try {
            return Base64.getUrlEncoder().withoutPadding().encodeToString(this.objectMapper.writeValueAsBytes(value));
        } catch (Exception ex) {
            throw new IllegalStateException("Could not encode JWT", ex);
        }
    }

    private String sign(String value) {
        try {
            var mac = Mac.getInstance(HMAC_ALGORITHM);
            mac.init(new SecretKeySpec(this.secret, HMAC_ALGORITHM));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(mac.doFinal(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception ex) {
            throw new IllegalStateException("Could not sign JWT", ex);
        }
    }
}
