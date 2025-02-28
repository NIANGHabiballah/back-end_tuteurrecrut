package com.unchk.Backend_recruteur.controllers;

import com.unchk.Backend_recruteur.models.Application;
import com.unchk.Backend_recruteur.models.User;
import com.unchk.Backend_recruteur.repository.ApplicationRepository;
import com.unchk.Backend_recruteur.repository.UserRepository;
import com.unchk.Backend_recruteur.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/apply")
    public ResponseEntity<?> applyForJob(@RequestBody Application application) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        application.setUser(user);
        application.setStatus("En cours de traitement");
        applicationRepository.save(application);

        return ResponseEntity.ok("Candidature soumise avec succès");
    }

    @GetMapping("/status")
    public ResponseEntity<?> getApplicationStatus() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Application application = applicationRepository.findByUser(user).orElse(null);

        if (application == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(application.getStatus());
    }
}