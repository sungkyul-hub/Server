package org.skuhub.skuhub.api.taxi.service;

import org.skuhub.skuhub.api.taxi.dto.request.TaxiCommentRequest;
import org.skuhub.skuhub.common.response.BaseResponse;

public interface TaxiCommentService {
    public BaseResponse<String> postTaxiComment(TaxiCommentRequest request, String userId);

    public BaseResponse<String> getTaxiComment(Long postId);
}
