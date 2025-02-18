package org.skuhub.skuhub.api.taxi.service;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiJoinRequest;
import org.skuhub.skuhub.api.taxi.dto.response.TaxiPostResponse;
import org.skuhub.skuhub.common.enums.exception.ErrorCode;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.skuhub.skuhub.common.utills.jwt.JWTUtil;
import org.skuhub.skuhub.exceptions.CustomException;
import org.skuhub.skuhub.model.taxi.TaxiJoinJpaEntity;
import org.skuhub.skuhub.model.taxi.TaxiShareJpaEntity;
import org.skuhub.skuhub.model.user.UserInfoJpaEntity;
import org.skuhub.skuhub.repository.taxi.TaxiJoinRepository;
import org.skuhub.skuhub.repository.taxi.TaxiShareRepository;
import org.skuhub.skuhub.repository.users.UserInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
@Getter
@Service
@Slf4j
public class TaxiSearchServiceImpl implements TaxiSearchService {
    private final TaxiShareRepository taxiShareRepository;

    public TaxiSearchServiceImpl(TaxiShareRepository taxiShareRepository) {
        this.taxiShareRepository = taxiShareRepository;
    }

    @Override
    public BaseResponse<List<TaxiPostResponse>> searchTaxiShare(String keyword) {
        return null;
    }
}
