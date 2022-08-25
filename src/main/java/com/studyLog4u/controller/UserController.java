package com.studyLog4u.controller;

import com.studyLog4u.common.ApiResponse;
import com.studyLog4u.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ApiResponse getUser(){
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.studyLog4u.entity.user.User user = userService.getUser(principal.getUsername());
        return ApiResponse.success("user", user);
    }
}
