package org.skuhub.skuhub.api.taxi.service;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiEditRequest;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiPostRequest;
import org.skuhub.skuhub.api.taxi.dto.response.TaxiPostResponse;
import org.skuhub.skuhub.common.enums.exception.ErrorCode;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.skuhub.skuhub.common.utills.jwt.JWTUtil;
import org.skuhub.skuhub.exceptions.CustomException;
import org.skuhub.skuhub.model.taxi.TaxiShareJpaEntity;
import org.skuhub.skuhub.model.user.UserInfoJpaEntity;
import org.skuhub.skuhub.repository.taxi.TaxiShareRepository;
import org.skuhub.skuhub.repository.users.UserInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;

@Getter
@Service
@Slf4j
public class TaxiPostService {

    private final JWTUtil jwtUtil;
    private final TaxiShareRepository taxiShareRepository;
    private final UserInfoRepository userInfoRepository;

    public TaxiPostService(TaxiShareRepository taxiShareRepository, UserInfoRepository userInfoRepository, JWTUtil jwtUtil) {
        this.taxiShareRepository = taxiShareRepository;
        this.userInfoRepository = userInfoRepository;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "게시글 작성", description = "택시합승 게시글을 올리는 API")
    public BaseResponse<String> postTaxiShare(TaxiPostRequest request, String authorizationHeader) {
        String token = authorizationHeader.trim().substring(7);
        String userId = jwtUtil.getClaims(token).getSubject();

        UserInfoJpaEntity userEntity = userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        TaxiShareJpaEntity entity = new TaxiShareJpaEntity();
        entity.setUserKey(userEntity);
        entity.setTitle(request.getTitle());
        entity.setDepartureLocation(request.getDepartureLocation());
        entity.setNumberOfPeople(request.getNumberOfPeople());
        entity.setRideTime(request.getRideTime());
        entity.setDescription(request.getDescription());

        try {
            TaxiShareJpaEntity saved = taxiShareRepository.save(entity);
            return new BaseResponse<>(true, "201", "택시합승 게시글 저장 성공", OffsetDateTime.now(), "게시글 작성 성공");
        } catch (Exception e) {
            return new BaseResponse<>(false, "500", "게시글 저장 실패", OffsetDateTime.now(), e.getMessage());
        }
    }

    @Operation(summary = "게시글 조회", description = "택시합승 게시글들을 조회하는 API")
    public BaseResponse<List<TaxiPostResponse>> getTaxiShare(){

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<TaxiPostResponse> taxiShares = taxiShareRepository.findAllByCreatedAtToday(startOfDay, endOfDay).stream().map(taxiShare -> {
            TaxiPostResponse response = new TaxiPostResponse();
            response.setPostId(taxiShare.getId());
            response.setName(taxiShare.getUserKey().getUserId()); // userId 설정
            response.setTitle(taxiShare.getTitle());
            response.setDepartureLocation(taxiShare.getDepartureLocation());
            response.setRideTime(taxiShare.getRideTime());
            response.setDescription(taxiShare.getDescription());
            response.setHeadCount(taxiShare.getHeadCount());
            response.setNumberOfPeople(taxiShare.getNumberOfPeople());
            LocalDateTime createdAt = taxiShare.getCreatedAt();
            OffsetDateTime offsetCreatedAt = createdAt.atOffset(ZoneOffset.UTC);
            response.setCreatedAt(offsetCreatedAt);
            return response;
        }).collect(Collectors.toList()).reversed();

        if(taxiShares.isEmpty()) {
            throw new CustomException(ErrorCode.NotFound, "게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }


        return new BaseResponse<>(true, "200", "택시합승 게시글 조회 성공", OffsetDateTime.now(), taxiShares);
    }

    public BaseResponse<String> postEditTaxiShare(TaxiEditRequest request, String authorizationHeader) {
        String token = authorizationHeader.trim().substring(7);
        String userId = jwtUtil.getClaims(token).getSubject();


        UserInfoJpaEntity userEntity = userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        TaxiShareJpaEntity postEntity = taxiShareRepository.findById(request.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if(postEntity.getUserKey() == null) {
            throw new CustomException(ErrorCode.NotFound, "게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }

        if(!postEntity.getUserKey().getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.Forbidden, "수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        TaxiShareJpaEntity entity = new TaxiShareJpaEntity();
        entity.setUserKey(userEntity);
        entity.setTitle(request.getTitle());
        entity.setDepartureLocation(request.getDepartureLocation());
        entity.setNumberOfPeople(request.getNumberOfPeople());
        entity.setRideTime(request.getRideTime());
        entity.setDescription(request.getDescription());
        entity.setUpdatedAt(LocalDateTime.now());

        try {
            TaxiShareJpaEntity saved = taxiShareRepository.save(entity);
            return new BaseResponse<>(true, "200", "택시합승 게시글 수정 성공", OffsetDateTime.now(), "게시글 수정 성공");
        } catch (Exception e) {
            return new BaseResponse<>(false, "500", "게시글 수정 실패", OffsetDateTime.now(), e.getMessage());
        }
    }
}
