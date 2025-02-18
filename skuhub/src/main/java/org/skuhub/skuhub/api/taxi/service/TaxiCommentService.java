package org.skuhub.skuhub.api.taxi.service;

import org.skuhub.skuhub.api.taxi.dto.request.TaxiCommentRequest;
import org.skuhub.skuhub.api.taxi.dto.response.TaxiCommentResponse;
import org.skuhub.skuhub.common.response.BaseResponse;

import java.util.List;

public interface TaxiCommentService {
    public BaseResponse<String> postTaxiComment(TaxiCommentRequest request, String userId);

    public BaseResponse<List<TaxiCommentResponse>> getTaxiComment(Long postId);
}
