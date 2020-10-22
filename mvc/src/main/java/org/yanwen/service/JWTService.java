package org.yanwen.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yanwen.model.Role;
import org.yanwen.model.User;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JWTService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private final String SECRET_KEY = System.getProperty("secret.key");
    private final String ISSUER = "com.ascending";
    private final long EXPIRATION_TIME = 86400 * 1000;

    public String generateToken(User user){
        //JWT signature algorithm using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //Sign JWT with SECRET_KEY
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary((SECRET_KEY));
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Payload （Claim）
        Claims claims = Jwts.claims();
        claims.setId(String.valueOf(user.getId()));
        claims.setSubject(user.getUser_name());
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setIssuer(ISSUER);
        claims.setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME));

        Set<Role> roles = user.getRoles();
        String allowedReadResources = "";
        String allowedCreateResources = "";
        String allowedUpdateResources = "";
        String allowedDeleteResources = "";

        String allowedResource = roles.stream().map(role->role.getAllowedResource()).collect(Collectors.joining(","));

        for (Role role : roles) {
            if (role.isAllowedRead()) allowedReadResources = allowedResource;
            if (role.isAllowedCreate()) allowedCreateResources = allowedResource;
            if (role.isAllowedUpdate()) allowedUpdateResources = allowedResource;
            if (role.isAllowedDelete()) allowedDeleteResources = allowedResource;
        }

        claims.put("allowedReadResources", allowedReadResources.replaceAll(",$", ""));
        claims.put("allowedCreateResources", allowedCreateResources.replaceAll(",$", ""));
        claims.put("allowedUpdateResources", allowedUpdateResources.replaceAll(",$", ""));
        claims.put("allowedDeleteResources", allowedDeleteResources.replaceAll(",$", ""));

        //Build the JWT and serializers it to a compact, URL-safe string
        JwtBuilder builder = Jwts.builder().setClaims(claims).signWith(signatureAlgorithm,signingKey);

        return builder.compact();
    }


    public Claims decryptJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token).getBody();
        logger.debug("Claims: " + claims.toString());
        return claims;
    }
}
