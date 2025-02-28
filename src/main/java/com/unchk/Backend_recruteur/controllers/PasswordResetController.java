package com.unchk.Backend_recruteur.controllers;

import com.unchk.Backend_recruteur.security.services.EmailService;
import com.unchk.Backend_recruteur.security.services.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/request")
    public ResponseEntity<?> requestPasswordReset(@RequestBody String email) {
        String token = passwordResetService.createPasswordResetToken(email);
        if (token == null) {
            return ResponseEntity.badRequest().body("E-mail introuvable");
        }
        String resetLink = "http://localhost:8080/api/password-reset/reset?token=" + token;
        emailService.sendSimpleMessage(email, "Demande de réinitialisation du mot de passe", "Pour réinitialiser votre mot de passe, cliquez sur le lien ci-dessous:\n" + resetLink);
        return ResponseEntity.ok("Lien de réinitialisation du mot de passe envoyé à votre adresse e-mail");
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestBody String newPassword) {
        boolean result = passwordResetService.resetPassword(token, newPassword);
        if (!result) {
            return ResponseEntity.badRequest().body("Jeton invalide");
        }
        return ResponseEntity.ok("Mot de passe réinitialisé avec succès");
    }
}