package com.unchk.Backend_recruteur.controllers;

import com.unchk.Backend_recruteur.models.User;
import com.unchk.Backend_recruteur.repository.UserRepository;
import com.unchk.Backend_recruteur.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("Utilisateur non trouvé avec cet e-mail");
        }

        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        userRepository.save(user);

        // Envoyer un e-mail avec le jeton de réinitialisation
        // emailService.sendSimpleMessage(user.getEmail(), "Réinitialisation de mot de passe", "Pour réinitialiser votre mot de passe, cliquez sur le lien suivant : http://localhost:8080/reset-password?token=" + resetToken);

        return ResponseEntity.ok("Un e-mail de réinitialisation de mot de passe a été envoyé");
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");

        User user = userRepository.findByResetToken(token).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("Jeton de réinitialisation invalide");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        userRepository.save(user);

        return ResponseEntity.ok("Mot de passe réinitialisé avec succès");
    }
}