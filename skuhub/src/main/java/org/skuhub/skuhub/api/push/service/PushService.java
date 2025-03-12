package org.skuhub.skuhub.api.push.service;

import org.skuhub.skuhub.common.response.BaseResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface PushService {

    BaseResponse<String> saveToken(String userId, String tokenRequest);

    BaseResponse<String> deleteToken(String userId);

    boolean pushKeywordAlarm(Long postId, String notice) throws IOException;

    boolean pushTaxiJoinAlarm(Long postId) throws IOException;

    boolean pushTaxiCommentAlarm(Long postId, String content) throws IOException;
}
