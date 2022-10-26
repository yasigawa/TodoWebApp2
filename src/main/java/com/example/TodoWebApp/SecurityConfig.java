package com.example.TodoWebApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//        .withUser("yama3")
//        .password(passwordEncoder().encode("123456"))
////        .password("$2a$10$E55vg96856cWy4oyAUpQ6OH2mxO6eTt43A5lPwa3MszPbDpAOPiLG")
//        .roles("USER");
//
        auth.userDetailsService(userDetailsService);
        
        System.out.println(new BCryptPasswordEncoder().encode("123456"));

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers("/signup").permitAll()
            .anyRequest()
            .authenticated();
        http.formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/")
            .permitAll();
        http.logout()
        	.permitAll();
    }

}
