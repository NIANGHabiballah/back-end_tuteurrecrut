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

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setProfilePicture(updatedUser.getProfilePicture());
        userRepository.save(user);

        return ResponseEntity.ok("L'utilisateur a été mis à jour avec succès");
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findById(userDetails.getId()).orElse(null);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        String ancienMotdePasse = request.get("ancienMotdePasse");
        String nouveauMotdePasse = request.get("nouveauMotdePasse");

        if (!passwordEncoder.matches(ancienMotdePasse, user.getPassword())) {
            return ResponseEntity.badRequest().body("Aancien mot de passe est incorrect");
        }

        user.setPassword(passwordEncoder.encode(nouveauMotdePasse));
        userRepository.save(user);

        return ResponseEntity.ok("Mot de passe modifié avec succès");
    }
}