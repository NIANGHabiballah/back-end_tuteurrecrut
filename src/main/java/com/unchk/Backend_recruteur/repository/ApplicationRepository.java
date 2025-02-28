package com.unchk.Backend_recruteur.repository;

import com.unchk.Backend_recruteur.models.Application;
import com.unchk.Backend_recruteur.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByUser(User user);
}