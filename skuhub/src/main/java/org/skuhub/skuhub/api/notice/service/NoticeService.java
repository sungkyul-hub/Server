package org.skuhub.skuhub.api.notice.service;
import org.skuhub.skuhub.api.notice.dto.response.NoticeResponse;
import org.skuhub.skuhub.common.response.BaseResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface NoticeService {
    public BaseResponse<List<NoticeResponse>> searchNotice(String keyword);

    public BaseResponse<List<NoticeResponse>> categoryNotice(String category);
}
