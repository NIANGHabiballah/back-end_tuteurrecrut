package com.unchk.Backend_recruteur.repository;

import java.util.Optional;

import com.unchk.Backend_recruteur.models.ERole;
import com.unchk.Backend_recruteur.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
