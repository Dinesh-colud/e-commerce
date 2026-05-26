package com.ecommerce.project.repositories;

import com.ecommerce.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean findByUserName(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
