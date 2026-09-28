package com.enigma.loan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagingResponse {
    private Integer currentPage;
    private Integer totalPage;
    private Integer size;
    private Long totalElements;
    private Boolean hasNext;
    private Boolean hasPrevious;
}
