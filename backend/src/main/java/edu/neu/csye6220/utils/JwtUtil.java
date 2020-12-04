package edu.neu.csye6220.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.neu.csye6220.models.User;

import java.util.Calendar;
import java.util.Date;

public class JwtUtil {
    private final static String secret = "edu.neu.csye6220";
    private final static String issuer = "localhost";
    private final static Algorithm algorithm = Algorithm.HMAC256(secret);

    public static String createToken(User u) {
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DATE, 1);
        Date expire = c.getTime();

        return JWT.create()
                .withIssuedAt(today)
                .withExpiresAt(expire)
                .withAudience(String.valueOf(u.getId()))
                .withIssuer(issuer)
                .withClaim("username", u.getUsername())
                .withClaim("email", u.getEmail())
                .withClaim("phone", u.getPhone())
                .sign(algorithm);
    }

    public static boolean verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
        Date today = new Date();
        return jwt.getIssuedAt().before(today) && jwt.getExpiresAt().after(today);
    }

    public static DecodedJWT decodeToken(String token) {
        return JWT.decode(token);
    }
}
