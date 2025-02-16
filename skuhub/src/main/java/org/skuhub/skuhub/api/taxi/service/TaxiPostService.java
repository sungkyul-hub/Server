package org.skuhub.skuhub.api.taxi.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiPostRequest;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.skuhub.skuhub.common.utills.jwt.JWTUtil;
import org.skuhub.skuhub.model.taxi.TaxiShareJpaEntity;
import org.skuhub.skuhub.model.user.UserInfoJpaEntity;
import org.skuhub.skuhub.repository.taxi.TaxiShareRepository;
import org.skuhub.skuhub.repository.users.UserInfoRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Getter
@Service
public class TaxiPostService {

    private  final JWTUtil jwtUtil;

    public TaxiPostService(TaxiShareRepository taxiShareRepository, UserInfoRepository userInfoRepository, JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    public static BaseResponse<String> postTaxiShare(TaxiPostRequest request, String authorizationHeader, UserInfoRepository userInfoRepository, TaxiShareRepository taxiShareRepository) {
//      String userId = jwtUtil.getClaims(authorizationHeader).getSubject();

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
