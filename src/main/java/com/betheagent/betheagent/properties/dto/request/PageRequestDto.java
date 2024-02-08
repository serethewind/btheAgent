package com.betheagent.betheagent.properties.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

/**
 * Data Transfer Object (DTO) representing the parameters for pagination and sorting in a pageable request.
 * The fields of the class includes
 * page number with a default value of 0
 * page size with a default value of 10
 * sort direction with a default value of ASCENDING
 * sort by column with a default value of id
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDto {

    private Integer pageNo = 0;
    private Integer pageSize = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortByColumn = "id";

    public Pageable getPageable() {
        return PageRequest.of(
                Optional.ofNullable(this.pageNo).orElse(0),
                Optional.ofNullable(this.pageSize).orElse(10),
                Optional.ofNullable(this.sortDirection).orElse(Sort.Direction.ASC),
                Optional.ofNullable(this.sortByColumn).orElse("id")
        );
    }
}
