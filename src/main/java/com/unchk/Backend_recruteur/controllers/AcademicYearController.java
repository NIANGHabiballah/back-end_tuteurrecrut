package com.unchk.Backend_recruteur.controllers;

import com.unchk.Backend_recruteur.models.AcademicYear;
import com.unchk.Backend_recruteur.repository.AcademicYearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academic-years")
public class AcademicYearController {
    @Autowired
    private AcademicYearRepository academicYearRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addAcademicYear(@RequestBody AcademicYear academicYear) {
        academicYearRepository.save(academicYear);
        return ResponseEntity.ok("Année académique ajoutée avec succès");
    }

    @GetMapping("/all")
    public ResponseEntity<List<AcademicYear>> getAllAcademicYears() {
        List<AcademicYear> academicYears = academicYearRepository.findAll();
        return ResponseEntity.ok(academicYears);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAcademicYear(@PathVariable Long id, @RequestBody AcademicYear updatedAcademicYear) {
        AcademicYear academicYear = academicYearRepository.findById(id).orElse(null);

        if (academicYear == null) {
            return ResponseEntity.notFound().build();
        }

        academicYear.setAnnee(updatedAcademicYear.getAnnee());
        academicYearRepository.save(academicYear);

        return ResponseEntity.ok("Année académique mise à jour avec succès");
    }
}