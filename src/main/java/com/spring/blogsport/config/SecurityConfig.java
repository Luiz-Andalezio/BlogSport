package com.spring.blogsport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.spring.blogsport.repository.UserRepository;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    private static final String[] PUBLIC_LIST = {
            "/",
            "/home",
            "/home/**",
            "/posts",
            "/posts/{id}",
            "/posts/**",
            "/categories",
            "/categories/{id}",
            "/categories/**",
            "/search",
            "/search/**",
            "/user",
            "/user/**",
            "/css/**",
            "/js/**",
            "/images/**",
            "/login",
            "/register",
            "/errors/",
            "/errors/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error", "/error/**").permitAll() 
                        .requestMatchers("/", "/home", "/home/**").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                        .requestMatchers("/login", "/register").permitAll()
                        .requestMatchers(PUBLIC_LIST).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            com.spring.blogsport.model.User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new com.spring.blogsport.security.CustomUserDetails(user);
        };
    }
}