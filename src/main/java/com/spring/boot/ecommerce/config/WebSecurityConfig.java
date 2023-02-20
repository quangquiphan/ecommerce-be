package com.spring.boot.ecommerce.config;

import com.spring.boot.ecommerce.config.jwt.JwtAuthenticationEntryPoint;
import com.spring.boot.ecommerce.config.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilterBean() throws Exception {
        return new JwtAuthenticationFilter();
    }

    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors().and()
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                // Allow access public resource
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/favicon.ico",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.png",
                        "/**/*.gif",
                        "/public/**",
                        "/**/*.json",
                        "/**/*.jpg",
                        // enable swagger endpoints
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/manage/api-docs"
                ).permitAll()
                // allow CORS option calls
                .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                .antMatchers(
                        "/api/**"

                ).permitAll() // All other request must be specify token
                .anyRequest().authenticated();

        // Custom JWT based security filter
        httpSecurity.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        httpSecurity.headers().cacheControl();
        httpSecurity.headers().frameOptions().disable();
    }
}


