package org.br.ct9backend.security.auth;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {
    @Bean
    public Dotenv dotenv() {
        String env = System.getenv("ENVIRONMENT");
        String envFile = env != null && env.equals("docker") ? ".env.docker" : ".env.local";
        return Dotenv.configure().filename(envFile).load();
    }
}