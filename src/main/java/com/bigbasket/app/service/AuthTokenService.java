package com.bigbasket.app.service;

import com.bigbasket.app.exception.AuthException;
import com.bigbasket.app.model.AuthToken;
import com.bigbasket.app.model.User;
import com.bigbasket.app.repository.AuthTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthTokenService {

    private final AuthTokenRepository repository;

    public AuthTokenService(AuthTokenRepository repository) {
        this.repository = repository;
    }

    public void saveConfirmationToken(AuthToken token) {
        repository.save(token);
    }

    public AuthToken getToken(User user) {
        return repository.findByUser(user);
    }

    public User getUser(String token) {
        final AuthToken authToken = repository.findByToken(token);

        if (authToken != null) {
            return authToken.getUser();
        } else {
            return null;
        }
    }

    public void authenticate(String token) throws AuthException {
        // null check
        if (Objects.isNull(token)) {
            // throw an exception
            throw new AuthException("token not present");
        }

        if (Objects.isNull(getUser(token))) {
            throw new AuthException("token not valid");
        }
    }
}
