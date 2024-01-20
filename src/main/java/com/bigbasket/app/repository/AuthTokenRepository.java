package com.bigbasket.app.repository;

import com.bigbasket.app.model.AuthToken;
import com.bigbasket.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Integer> {

    AuthToken findByUser(User user);

    AuthToken findByToken(String token);
}
