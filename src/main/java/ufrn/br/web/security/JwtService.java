package ufrn.br.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import ufrn.br.web.WebApplication;
import ufrn.br.web.model.Usuario;

import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import io.jsonwebtoken.io.Decoders;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}") //injetando propriedades do applications.properties
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;


    public String gerarToken(Usuario usuario) {
        long expString = Long.valueOf(expiracao);
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant(); //localdatetime to objeto instante
        Date data = Date.from(instant); //a forma de passar para o jwt a data de expiração

        //chave de assinatura
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(chaveAssinatura));

        return Jwts //retorna o jwt
                .builder()
                .setSubject(usuario.getLogin()) //colocar informação útil para o token
                .setExpiration(data) //data de expieração
                .signWith(key) //assinatura do token (recebe parametro key)
                .compact();
    }
    //https://jwt.io


    private Claims obterClaims(String token) throws ExpiredJwtException { //lança erro caso o token tenha sido expirado

        //chave de assinatura
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(chaveAssinatura));

        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValido(String token) {
        try {
            Claims claims = (Claims) obterClaims(token);
            Date dataExpiracao = claims.getExpiration();
            LocalDateTime data =
                    dataExpiracao.toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(data); //data atual nao é depois da data de expiração
        } catch (Exception e) { //se ocorrer uma exceção o token não é mais válido
            return false;
        }
    }


    public String obterLoginUsuario(String token) throws ExpiredJwtException {
        return (String) (obterClaims(token)).getSubject();
    }

    public static void main(String[] args) {



    }
}
