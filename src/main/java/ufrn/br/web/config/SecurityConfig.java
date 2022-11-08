package ufrn.br.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import ufrn.br.web.security.JwtAuthFilter;
import ufrn.br.web.security.JwtService;
import ufrn.br.web.services.UsuarioService;

@EnableWebSecurity 
public class SecurityConfig {

    private final JwtService jwtService;

    private final UsuarioService usuarioService;

    public SecurityConfig(JwtService jwtService, UsuarioService usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() 
                .authorizeHttpRequests((authz) -> {
                            try {
                                authz
                                        .antMatchers("/api/pessoas/**").hasAnyRole("ADMIN")
                                        .antMatchers(HttpMethod.GET, "/api/emprestimos/**").hasAnyRole("ADMIN", "USER", "MODERADOR")
                                        .antMatchers(HttpMethod.POST, "/api/livros/**").hasAnyRole("ADMIN")
                                        .antMatchers(HttpMethod.GET, "/api/livros/**").hasAnyRole("ADMIN", "USER", "MODERADOR")
                                        .antMatchers(HttpMethod.GET, "/api/endereco/**").hasAnyRole("ADMIN", "USER", "MODERADOR")
                                        .antMatchers(HttpMethod.POST, "/api/endereco/**").hasAnyRole("ADMIN", "USER", "MODERADOR")
                                        .antMatchers(HttpMethod.POST, "/api/emprestimos/**").hasAnyRole("ADMIN", "USER", "MODERADOR")
                                        .antMatchers(HttpMethod.POST, "/api/usuarios/**")
                                            .permitAll()
                                            .anyRequest().authenticated()
                                        .and()
                                        .sessionManagement()
                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //sessões sem usuários - TODA REQUISICAO PRECISA DO TOKEN
                                        .and() 
                                        .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                );
        return http.build();
    }
}
