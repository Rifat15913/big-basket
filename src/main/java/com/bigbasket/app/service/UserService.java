package com.bigbasket.app.service;

import com.bigbasket.app.dto.ResponseDto;
import com.bigbasket.app.dto.user.SignInDto;
import com.bigbasket.app.dto.user.SignInReponseDto;
import com.bigbasket.app.dto.user.SignUpDto;
import com.bigbasket.app.exception.AuthException;
import com.bigbasket.app.exception.CustomException;
import com.bigbasket.app.model.AuthToken;
import com.bigbasket.app.model.User;
import com.bigbasket.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final AuthTokenService authService;

    public UserService(UserRepository userRepository, AuthTokenService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @Transactional
    public ResponseDto signUp(SignUpDto dto) {
        // check if user is already present
        if (Objects.nonNull(userRepository.findByEmail(dto.getEmail()))) {
            // we have an user
            throw new CustomException("user already present");
        }


        // hash the password

        String encryptedpassword = dto.getPassword();

        try {
            encryptedpassword = hashPassword(dto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User user = new User(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                encryptedpassword
        );

        userRepository.save(user);

        // save the user

        // create the token

        final AuthToken authToken = new AuthToken(user);

        authService.saveConfirmationToken(authToken);

        return new ResponseDto("success", "user created successfully");
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SignInReponseDto signIn(SignInDto dto) {
        // find user by email

        User user = userRepository.findByEmail(dto.getEmail());

        if (Objects.isNull(user)) {
            throw new AuthException("user is not valid");
        }

        // hash the password

        try {
            if (!user.getPassword().equals(hashPassword(dto.getPassword()))) {
                throw new AuthException("wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // compare the password in DB

        // if password match

        AuthToken token = authService.getToken(user);

        // retrieve the token

        if (Objects.isNull(token)) {
            throw new CustomException("token is not present");
        }

        return new SignInReponseDto("success", token.getToken());

        // return response
    }
}
