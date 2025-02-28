package com.unchk.Backend_recruteur.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
  @GetMapping("/all")
  public String allAccess() {
    return "Contenu public.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('CANDIDAT')or hasRole('RECRUTEUR') or hasRole('ADMIN')")
  public String userAccess() {
    return "Contenu utilisateur.";
  }

  @GetMapping("/candidat")
  @PreAuthorize("hasRole('CANDIDAT')")
  public String candidatAccess() {
    return "Conseil des candidats.";
  }
  @GetMapping("/recruteur")
  @PreAuthorize("hasRole('RECRUTEUR')")
  public String recruteurAccess() {
    return "Conseil des recrutement.";
  }


  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Conseil d'administration.";
  }
}
