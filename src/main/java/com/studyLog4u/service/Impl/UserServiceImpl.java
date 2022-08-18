package com.studyLog4u.service.Impl;

import com.studyLog4u.entity.user.User;
import com.studyLog4u.repository.UserRepository;
import com.studyLog4u.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUser(String userId) {
        log.info("UserServiceImpl: getUser...");
        return userRepository.findByUserId(userId);
    }
}
