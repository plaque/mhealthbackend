package plaque.mHelathBackEnd.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import plaque.mHelathBackEnd.Security.JWTAuthenticationFilter;
import plaque.mHelathBackEnd.Security.JWTAuthorizationFilter;

import static plaque.mHelathBackEnd.Security.SecurityConstants.SIGN_UP_URL;

/**
 * Created by Szymon on 2016-10-13.
 */
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Configuration
    public static class ApiSecurityConfig extends WebSecurityConfigurerAdapter{

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception{

             httpSecurity.cors().and().csrf().disable().authorizeRequests()
                        .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                        .anyRequest().authenticated()
                        .and()
                        .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                        .addFilter(new JWTAuthorizationFilter(authenticationManager()));


         }
    }
}
