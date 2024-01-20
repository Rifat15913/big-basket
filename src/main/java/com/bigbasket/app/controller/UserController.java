package com.bigbasket.app.controller;

import com.bigbasket.app.dto.ResponseDto;
import com.bigbasket.app.dto.user.SignInDto;
import com.bigbasket.app.dto.user.SignInReponseDto;
import com.bigbasket.app.dto.user.SignUpDto;
import com.bigbasket.app.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignUpDto dto) {
        return userService.signUp(dto);
    }

    @PostMapping("/signin")
    public SignInReponseDto signIn(@RequestBody SignInDto dto) {
        return userService.signIn(dto);
    }


}
