package com.alkl1m.contractor;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class JwtUtil {

    private static final String SECRET_KEY = "YiJW5JhK3uv6q8InoJgQYP3hmOEP/79009Y/6/Xeamxq9DnYSVZzU1m5iFRCNWymmBAmLmYmY2C1KcSqX5aELlN7fkLdJtehwaSjWotfrESvUIap6VI9GyMglmeuBPTfFQMHJM7qhLwZQXHIrA3sRjvSrDHNTffuEHmKqtWuXgIX%";

    private JwtUtil() {}

    public static String generateJwt(String username, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 86400000L))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}