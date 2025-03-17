package org.br.ct9backend.security;

import org.br.ct9backend.aluno.repository.AlunoRepository;
import org.br.ct9backend.security.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private FirebaseTokenFilter firebaseTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/verifyRole","/aluno/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(firebaseTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(AlunoRepository alunoRepository) {
        return username -> {
            User user = alunoRepository.findByFirebaseUid(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with firebaseUid: " + username));
            return org.springframework.security.core.userdetails.User.withUsername(user.getFirebaseUid())
                    .authorities(user.getCargo().name())
                    .build();
        };
    }
}