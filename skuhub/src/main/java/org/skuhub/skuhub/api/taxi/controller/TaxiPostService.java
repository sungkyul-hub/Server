package org.skuhub.skuhub.api.taxi.controller;

import io.jsonwebtoken.Jwt;
import jakarta.persistence.EntityNotFoundException;
import org.skuhub.skuhub.api.auth.service.AuthService;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiPostRequest;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.skuhub.skuhub.common.utills.jwt.dto.JwtDto;
import org.skuhub.skuhub.model.taxi.TaxiShareJpaEntity;
import org.skuhub.skuhub.model.user.UserInfoJpaEntity;
import org.skuhub.skuhub.repository.taxi.TaxiShareRepository;
import org.skuhub.skuhub.repository.users.UserInfoRepository;

import java.time.OffsetDateTime;

public class TaxiPostService {
    private final TaxiShareRepository taxiShareRepository;
    private final UserInfoRepository userInfoRepository;

    public TaxiPostService(TaxiShareRepository taxiShareRepository, UserInfoRepository userInfoRepository, AuthService authService) {
        this.taxiShareRepository = taxiShareRepository;
        this.userInfoRepository = userInfoRepository;
    }


    public static BaseResponse<String> postTaxiShare(TaxiPostRequest request, TaxiShareRepository taxiShareRepository, UserInfoRepository userInfoRepository) {
        UserInfoJpaEntity userEntity = userInfoRepository.findByUserId(request.getUserId())
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
            return new BaseResponse<>(true, "200", "택시합승 게시글 저장 성공", OffsetDateTime.now(), "게시글 작성 성공");
        } catch (Exception e) {
            return new BaseResponse<>(false, "500", "게시글 저장 실패", OffsetDateTime.now(), e.getMessage());
        }
    }
}
