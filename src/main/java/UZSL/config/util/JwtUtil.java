package UZSL.config.util;

import UZSL.dto.auth.JwtDTO;
import UZSL.enums.UzSlRoles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JwtUtil {

    private static final int tokenLiveTime = 1000 * 3600 * 24; // 1-day
    private static final String secretKey = "U6zBr2R9sFpAq7mLdEvKjNxPwYtGhZmXcVbJnQoTsWaSdFgHjKlMzXyCrVuBpLkJ";

    public static String encode(String username, Integer id, UzSlRoles uzSlRoles) {
        Map<String, String> extraClaims = new HashMap<>();
        extraClaims.put("userRole", uzSlRoles.name());
        extraClaims.put("userId", String.valueOf(id));
        extraClaims.put("randomData", UUID.randomUUID().toString().repeat(8));
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenLiveTime))
                .signWith(getSignInKey())
                .compact();
    }

    public static JwtDTO decode(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String username = claims.getSubject();
        Integer id = Integer.valueOf((String) claims.get("userId"));
        String strRole = (String) claims.get("userRole");
        UzSlRoles roleEnum = UzSlRoles.valueOf(strRole);
        return new JwtDTO(id, username, roleEnum);
    }

    private static SecretKey getSignInKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
