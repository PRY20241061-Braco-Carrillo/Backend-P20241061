package com.p20241061.shared.utils;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;

@Getter
public class PaginatedRequest {
    private Integer pageNumber = 0;
    private Integer pageSize = 5;
    private String sortBy = "";
    private Boolean isAscending = Boolean.TRUE;
    private final PageRequest pageRequest;
    private long offset;
    private Integer limit;

    public PaginatedRequest() {
        this.pageRequest = setPageRequest();
        setOffsetAndLimit();
    }

    public PaginatedRequest(Integer pageNumber, Integer pageSize, String sortBy, Boolean isAscending) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.isAscending = isAscending;
        this.pageRequest = setPageRequest();
        setOffsetAndLimit();
    }

    private PageRequest setPageRequest() {
        Sort sort = Boolean.TRUE.equals(isAscending) ? Sort.by(Sort.Order.asc(sortBy)) : Sort.by(Sort.Order.desc(sortBy));
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    private void setOffsetAndLimit() {
        this.offset = pageRequest.getOffset();
        this.limit = pageSize;
    }

    public <T> Flux<T> paginateData(Flux<T> data) {
        return data.skip(offset).take(limit);
    }
}
