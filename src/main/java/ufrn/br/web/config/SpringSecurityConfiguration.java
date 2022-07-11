package ufrn.br.web.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .
                anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successForwardUrl("/pessoas/login/")
                .and()
                .logout().    //logout configuration
                logoutUrl("/appLogout").
                logoutSuccessUrl("/login")
                .permitAll();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/webjars/**");
    }
}
