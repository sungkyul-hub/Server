package org.skuhub.skuhub.api.taxi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.skuhub.skuhub.api.Sample.dto.request.SamplePostRequest;
import org.skuhub.skuhub.api.Sample.dto.response.SampleResponse;
import org.skuhub.skuhub.model.sample.SampleJpaEntity;
import org.skuhub.skuhub.repository.sample.SampleRepository;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.List;  // List를 임포트

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/taxi/posts")
@Slf4j
public class TaxiCommentController {
}
