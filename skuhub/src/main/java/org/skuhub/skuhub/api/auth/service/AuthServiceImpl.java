package org.skuhub.skuhub.api.auth.service;

import lombok.RequiredArgsConstructor;
import org.skuhub.skuhub.api.auth.dto.request.SignupRequest;
import org.skuhub.skuhub.api.auth.dto.request.LoginRequest;
import org.skuhub.skuhub.common.enums.exception.ErrorCode;
import org.skuhub.skuhub.common.utills.jwt.JWTUtil;
import org.skuhub.skuhub.common.utills.jwt.dto.JwtDto;
import org.skuhub.skuhub.exceptions.CustomException;
import org.skuhub.skuhub.model.user.UserInfoJpaEntity;
import org.skuhub.skuhub.repository.users.UserInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserInfoRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Override
    @Transactional
    public JwtDto signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.Conflict, "이미 존재하는 회원입니다.", HttpStatus.CONFLICT);
        }

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        String userId = generateUserIdFromEmail(request.getEmail());

        UserInfoJpaEntity user = UserInfoJpaEntity.builder()
                .email(request.getEmail())
                .password(encryptedPassword)
                .year(request.getYear())
                .department(request.getDepartment())
                .name(request.getName())
                .userId(userId)  // userId 설정
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return jwtUtil.generateJwtDto(user.getUserId(), "USER");
    }

    @Override
    public JwtDto login(LoginRequest request) {
        Optional<UserInfoJpaEntity> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            throw new CustomException(ErrorCode.NotFound, "사용자가 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        UserInfoJpaEntity user = userOptional.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.Unauthorized, "비밀번호를 확인해 주세요.", HttpStatus.UNAUTHORIZED);
        }

        return jwtUtil.generateJwtDto(user.getUserId(), "USER");
    }

    @Override
    public JwtDto reissue(String refreshToken) {
        if (!jwtUtil.isValidRefreshToken(refreshToken)) {
            throw new CustomException(ErrorCode.Conflict, "올바른 Refresh 토큰이 아닙니다.", HttpStatus.CONFLICT);
        }
        String userId = jwtUtil.getClaims(refreshToken).getSubject();
        return jwtUtil.generateJwtDto(userId, "USER");
    }

    @Override
    public boolean getEmailValidity(String email) {
        return !userRepository.existsByEmail(email);
    }

    private String generateUserIdFromEmail(String email) {
        return email.split("@")[0]; // 예: email@example.com -> "email"
    }
}
