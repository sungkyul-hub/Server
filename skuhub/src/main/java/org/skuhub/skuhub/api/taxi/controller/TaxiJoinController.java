package org.skuhub.skuhub.api.taxi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.taxi.service.TaxiPostService;
import org.springframework.web.bind.annotation.*;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;  // List를 임포트

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/api/v1/taxi")
@Slf4j
public class TaxiJoinController {



}
