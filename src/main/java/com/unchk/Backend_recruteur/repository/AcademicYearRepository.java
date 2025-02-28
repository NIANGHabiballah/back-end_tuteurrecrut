package com.unchk.Backend_recruteur.repository;

import com.unchk.Backend_recruteur.models.AcademicYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {
}