package com.unchk.Backend_recruteur.config;

import com.unchk.Backend_recruteur.models.ERole;
import com.unchk.Backend_recruteur.models.Role;
import com.unchk.Backend_recruteur.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            for (ERole role : ERole.values()) {
                if (roleRepository.findByName(role).isEmpty()) {
                    roleRepository.save(new Role(role));
                }
            }
        };
    }
}


// Removed SecurityConfig class
