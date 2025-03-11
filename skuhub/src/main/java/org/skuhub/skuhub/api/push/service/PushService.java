package org.skuhub.skuhub.api.push.service;

import com.google.api.client.auth.oauth2.TokenRequest;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public interface PushService {

    BaseResponse<String> saveToken(String userId, String tokenRequest);

    BaseResponse<String> deleteToken(String userId);
}
