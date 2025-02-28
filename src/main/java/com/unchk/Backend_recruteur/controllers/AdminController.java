package com.unchk.Backend_recruteur.controllers;

import com.unchk.Backend_recruteur.models.Application;
import com.unchk.Backend_recruteur.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @GetMapping("/applications")
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationRepository.findAll();
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/applications/{id}/status")
    public ResponseEntity<?> updateApplicationStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Application application = applicationRepository.findById(id).orElse(null);

        if (application == null) {
            return ResponseEntity.notFound().build();
        }

        String status = request.get("status");
        String motif = request.get("motif");

        application.setStatus(status);
        application.setMotif(motif);
        applicationRepository.save(application);

        // Envoyer une notification par email au candidat
        // emailService.sendSimpleMessage(application.getUser().getEmail(), "Statut de votre candidature", "Votre candidature a été " + status + ". Motif : " + reason);

        return ResponseEntity.ok("Statut de la candidature mis à jour avec succès");
    }
}