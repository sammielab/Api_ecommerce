package proyecto_pd_dh.configuration;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "e1ab24d367fba6a87cd7a9d139b1157fa8dcb7c2c1ca097b684c3014fa2dc54a";


    public String extractUserName(String token){
        return extractClaims(token, Claims::getSubject);
    }

    //Metodo que extraiga un solo claim (nombre usuario)
    public<T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    //Metodo que extrae todos los claims del token
    public Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token) //Ojo esto tiene que ser de tipo string 
                .getBody();
    }

    //A partir del userDetails token
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }


    //A partir de todos los claims
    //Recibe por parametro los claims
    public String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userdetails
    ){
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userdetails.getUsername()) //obtenemos el nombre del usuario
                .setIssuedAt(new Date(System.currentTimeMillis())) //fecha de vigencia
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 365))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) //Firma del token y se le pasa el algoritmo de encryptacion
                .compact();

        //.setExpiration(new Date(System.currentTimeMillis() +1000*60*24)) //Fecha de expiracion que necesitamos. A partir de ahora le agregamos

    }

    //Decodifica la clave y retorna esa clave usando el algoritmo para tokens
    private Key getSignInKey(){
        //Variable de tipo byte que equivale a una base 64 y a los decodificadores
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }



    //Validaciones

    //El token es del usuario quien dice ser, recibimos el token y el user datails,
    public boolean isTokenValid(String token, UserDetails userDetails){
            final String username= extractUserName(token); //extaemos el username
            return (username.equals(userDetails.getUsername())) && !isTokenExpired(token); //Validamos que el el username sea el mismo y que el token no haya expirado
    }


    public boolean isTokenExpired(String token){
        return extractExpiracion(token).before(new Date());
    }

    private Date extractExpiracion(String token){
        return extractClaims(token, Claims::getExpiration);
    }
}


