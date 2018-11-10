package com.loona.hachathon;

import com.loona.hachathon.authentication.JwtAuthenticationManager;
import com.loona.hachathon.authentication.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationManager jwtAuthenticationManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
           .csrf()
                .disable();
        http
           .addFilterAfter(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http
           .authorizeRequests()
           .antMatchers("/authentication", "/image/**", "/test").permitAll()
           .anyRequest().authenticated();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter();
        jwtTokenFilter.setAuthenticationManager(jwtAuthenticationManager);
        return jwtTokenFilter;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
