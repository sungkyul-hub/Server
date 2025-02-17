package org.skuhub.skuhub.api.user.service;

import org.skuhub.skuhub.model.user.UserInfoJpaEntity;
import org.skuhub.skuhub.repository.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String findUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserInfoJpaEntity::getUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public boolean isUserValid(String userId) {
        return userRepository.existsByUserId(userId); // userId가 존재하는지 확인
    }
}