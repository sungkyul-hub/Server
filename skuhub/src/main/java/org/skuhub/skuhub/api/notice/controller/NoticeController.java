package org.skuhub.skuhub.api.notice.controller;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiEditRequest;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiPostDeleteRequest;
import org.skuhub.skuhub.api.taxi.dto.request.TaxiPostRequest;
import org.skuhub.skuhub.api.taxi.dto.response.TaxiPostDetailsResponse;
import org.skuhub.skuhub.api.taxi.dto.response.TaxiPostResponse;
import org.skuhub.skuhub.api.taxi.service.TaxiPostServiceImpl;
import org.skuhub.skuhub.common.utills.jwt.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.skuhub.skuhub.common.response.BaseResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/notice")
@Slf4j
public class NoticeController {

}
