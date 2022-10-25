package ufrn.br.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity //configuracao de segurança
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //seguranca entre aplicavao web e api,aqui nossa api é rest
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers("/api/pessoas/**")
                        .hasAnyRole("ADMIN")
                        .antMatchers(HttpMethod.GET,"/api/emprestimos/**")
                        .hasAnyRole("ADMIN", "USER", "MODERADOR")
                        .antMatchers(HttpMethod.POST,"/api/emprestimos/**")
                        .hasAnyRole("ADMIN")
                        .antMatchers(HttpMethod.POST, "/api/usuarios/**")
                        .permitAll()
                        .anyRequest().authenticated()

                )
                .httpBasic();
                return http.build();
    }
}
