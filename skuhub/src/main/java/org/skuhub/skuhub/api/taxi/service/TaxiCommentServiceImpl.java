package org.skuhub.skuhub.api.taxi.service;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiCommentRequest;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.skuhub.skuhub.common.utills.jwt.JWTUtil;
import org.skuhub.skuhub.model.user.UserInfoJpaEntity;
import org.skuhub.skuhub.repository.taxi.TaxiShareRepository;
import org.skuhub.skuhub.repository.users.UserInfoRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;

@Getter
@Service
@Slf4j
public class TaxiCommentServiceImpl implements TaxiCommentService{
    private final JWTUtil jwtUtil;
    private final TaxiShareRepository taxiShareRepository;
    private final UserInfoRepository userInfoRepository;

    public TaxiCommentServiceImpl(JWTUtil jwtUtil, TaxiShareRepository taxiShareRepository, UserInfoRepository userInfoRepository) {
        this.jwtUtil = jwtUtil;
        this.taxiShareRepository = taxiShareRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @Operation(summary = "댓글 작성", description = "택시합승 게시글에 댓글을 작성하는 API")
    public BaseResponse<String> postTaxiComment(TaxiCommentRequest request, String userId) {
        UserInfoJpaEntity userEntity = userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));


        return new BaseResponse<>(true, "201", "택시합승 댓글 저장 성공", OffsetDateTime.now(), "댓글 작성 성공");
    }
}
