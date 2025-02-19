package org.skuhub.skuhub.api.notice.service;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.skuhub.skuhub.api.notice.dto.response.NoticeDetailsResponse;
import org.skuhub.skuhub.api.notice.dto.response.NoticeResponse;
import org.skuhub.skuhub.common.enums.exception.ErrorCode;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.skuhub.skuhub.common.utills.jwt.JWTUtil;
import org.skuhub.skuhub.exceptions.CustomException;
import org.skuhub.skuhub.repository.notice.NoticeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.codehaus.groovy.runtime.DefaultGroovyMethods.collect;

@Getter
@Service
@Slf4j
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeServiceImpl(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Override
    public BaseResponse<List<NoticeResponse>> searchNotice(String keyword) {
        List<NoticeResponse> noticeList = noticeRepository.findByTitle(keyword).stream().map(notice -> {

            NoticeResponse response = new NoticeResponse();

            response.setNoticeId(notice.getId());
            response.setCategory(notice.getNoticeCategory());
            response.setTitle(notice.getTitle());
            response.setNoticeModifyDate(notice.getNoticeModifyDate());
            response.setWriter(notice.getWriter());

            // content를 30글자로 제한
            String content = notice.getNoticeContent();
            response.setContent(content.length() > 50 ? content.substring(0, 50) : content);

            return response;
        }).collect(Collectors.toList()).reversed();


        if(noticeList.isEmpty()) {
            throw new CustomException(ErrorCode.NotFound, "검색 결과가 없습니다.", HttpStatus.NOT_FOUND);
        }

        return new BaseResponse<>(true, "200", "공지사항 검색 성공", OffsetDateTime.now(), noticeList);
    }

    @Override
    public BaseResponse<List<NoticeResponse>> categoryNotice(String category) {
        List<NoticeResponse> noticeList = noticeRepository.findByCategory(category).stream().map(notice -> {

            NoticeResponse response = new NoticeResponse();

            response.setNoticeId(notice.getId());
            response.setCategory(notice.getNoticeCategory());
            response.setTitle(notice.getTitle());
            response.setNoticeModifyDate(notice.getNoticeModifyDate());
            response.setWriter(notice.getWriter());

            // content를 30글자로 제한
            String content = notice.getNoticeContent();
            response.setContent(content.length() > 50 ? content.substring(0, 50) : content);

            return response;
        }).collect(Collectors.toList()).reversed();


        if(noticeList.isEmpty()) {
            throw new CustomException(ErrorCode.NotFound, "카테고리 결과가 없습니다.", HttpStatus.NOT_FOUND);
        }

        return new BaseResponse<>(true, "200", "공지사항 카테고리 검색 성공", OffsetDateTime.now(), noticeList);
    }

    @Override
    public BaseResponse<NoticeDetailsResponse> detailNotice(Long noticeId) {
        NoticeDetailsResponse noticeDetailsResponse = noticeRepository.findById(noticeId).map(notice -> {
            NoticeDetailsResponse response = new NoticeDetailsResponse();

            response.setNoticeId(notice.getId());
            response.setCategory(notice.getNoticeCategory());
            response.setTitle(notice.getTitle());
            response.setNoticeModifyDate(notice.getNoticeModifyDate());
            response.setWriter(notice.getWriter());
            response.setContent(notice.getNoticeContent());
            response.setNoticeOriginalContent(notice.getNoticeOriginalContent());
            response.setUrl(notice.getUrl());

            return response;
        }).orElseThrow(() -> new CustomException(ErrorCode.NotFound, "공지사항을 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        return new BaseResponse<>(true, "200", "공지사항 상세보기 성공", OffsetDateTime.now(), noticeDetailsResponse);
    }
}
