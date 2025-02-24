package org.skuhub.skuhub.api.timetable.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.skuhub.skuhub.api.timetable.dto.request.CompletionRequest;
import org.skuhub.skuhub.model.timetable.CompletionEntity;
import org.skuhub.skuhub.model.timetable.UserInfoTb;
import org.skuhub.skuhub.repository.timetable.CompletionRepository;
import org.skuhub.skuhub.repository.timetable.UserInfoRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompletionService {

    private final CompletionRepository completionRepository;
    private final UserInfoRepository userInfoRepository;

    /**
     * 사용자 이수구분 입력
     */
    @Transactional
    public CompletionEntity saveCompletionData(CompletionRequest request) {
        Optional<UserInfoTb> userOptional = userInfoRepository.findById(request.getUserKey());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 user_key입니다.");
        }

        UserInfoTb user = userOptional.get();
        Optional<CompletionEntity> existingCompletion = completionRepository.findByUserInfoTb_UserKey(request.getUserKey());

        CompletionEntity completion;
        if (existingCompletion.isPresent()) {
            // 기존 데이터가 존재하면 업데이트
            completion = existingCompletion.get();
        } else {
            // 새로운 데이터 생성
            completion = new CompletionEntity();
            completion.setUserInfoTb(user);
        }

        completion.setMajorRequired(request.getMajorRequired());
        completion.setMajorElective(request.getMajorElective());
        completion.setGeneralRequired(request.getGeneralRequired());
        completion.setGeneralElective(request.getGeneralElective());
        completion.setMajorRequiredReq(request.getMajorRequiredReq());
        completion.setMajorElectiveReq(request.getMajorElectiveReq());
        completion.setGeneralRequiredReq(request.getGeneralRequiredReq());
        completion.setGeneralElectiveReq(request.getGeneralElectiveReq());
        completion.setOther(request.getOther());
        completion.setEarnedCredits(request.getEarnedCredits());
        completion.setGraduationCredits(request.getGraduationCredits());
        completion.setAvgScore(request.getAvgScore());
        completion.setUpdatedAt(Instant.now());

        return completionRepository.save(completion);
    }

    /**
     * 이수구분 정보 수정 기능
     */
    @Transactional
    public CompletionEntity updateCompletionData(CompletionRequest request) {
        Optional<CompletionEntity> existingCompletion = completionRepository.findByUserInfoTb_UserKey(request.getUserKey());

        if (existingCompletion.isEmpty()) {
            throw new IllegalArgumentException("해당 user_key에 대한 이수구분 정보를 찾을 수 없습니다.");
        }

        CompletionEntity completion = existingCompletion.get();

        // 요청된 값이 존재하면 업데이트
        if (request.getMajorRequired() != null) {
            completion.setMajorRequired(request.getMajorRequired());
        }
        if (request.getMajorElective() != null) {
            completion.setMajorElective(request.getMajorElective());
        }
        if (request.getGeneralRequired() != null) {
            completion.setGeneralRequired(request.getGeneralRequired());
        }
        if (request.getGeneralElective() != null) {
            completion.setGeneralElective(request.getGeneralElective());
        }
        if (request.getMajorRequiredReq() != null) {
            completion.setMajorRequiredReq(request.getMajorRequiredReq());
        }
        if (request.getMajorElectiveReq() != null) {
            completion.setMajorElectiveReq(request.getMajorElectiveReq());
        }
        if (request.getGeneralRequiredReq() != null) {
            completion.setGeneralRequiredReq(request.getGeneralRequiredReq());
        }
        if (request.getGeneralElectiveReq() != null) {
            completion.setGeneralElectiveReq(request.getGeneralElectiveReq());
        }
        if (request.getOther() != null) {
            completion.setOther(request.getOther());
        }
        if (request.getEarnedCredits() != null) {
            completion.setEarnedCredits(request.getEarnedCredits());
        }
        if (request.getGraduationCredits() != null) {
            completion.setGraduationCredits(request.getGraduationCredits());
        }
        if (request.getAvgScore() != null) {
            completion.setAvgScore(request.getAvgScore());
        }

        completion.setUpdatedAt(Instant.now()); // 수정 시간 업데이트

        return completionRepository.save(completion);
    }
}
