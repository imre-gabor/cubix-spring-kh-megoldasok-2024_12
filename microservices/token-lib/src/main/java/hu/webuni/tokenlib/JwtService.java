package hu.webuni.tokenlib;


import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;

@Service
public class JwtService {

    private static final String AUTH = "auth";
    //private Algorithm alg = Algorithm.HMAC256("mysecret");
    private Algorithm signerAlg;
    private Algorithm validatorAlg;
    
    @Value("${hu.webuni.tokenlib.keypaths.private:#{null}}")
    private String pathToPemWithPrivateKey;
    @Value("${hu.webuni.tokenlib.keypaths.public:#{null}}")
    private String pathToPemWithPublicKey;
    
    private String issuer = "webuni-user-service";
    
    @PostConstruct
    public void init() throws Exception {
    	
    	if(pathToPemWithPrivateKey != null) {
    		PrivateKey privateKey = PemUtils.getPrivateKey(pathToPemWithPrivateKey);
    		signerAlg = Algorithm.ECDSA512(null, (ECPrivateKey) privateKey);
    	}
    	
    	if(pathToPemWithPublicKey != null) {
    		PublicKey publicKey = PemUtils.getPublicKey(pathToPemWithPublicKey);
    		validatorAlg = Algorithm.ECDSA512((ECPublicKey) publicKey, null);
    	}
    }
    
    
    public String creatJwtToken(UserDetails principal) {
        return JWT.create()
            .withSubject(principal.getUsername())
            .withArrayClaim(AUTH, principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
            .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(20)))
            .withIssuer(issuer)
            .sign(signerAlg);
        
    }

    public UserDetails parseJwt(String jwtToken) {
        
        DecodedJWT decodedJwt = JWT.require(validatorAlg)
            .withIssuer(issuer)
            .build()
            .verify(jwtToken);
        return new User(decodedJwt.getSubject(), "dummy", 
                decodedJwt.getClaim(AUTH).asList(String.class)
                .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                );
        
    }

}