package co.kr.category.dto;

import co.kr.category.enums.CategoryStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CategoryResponseDto {
    private Long id;

    private String catName;

    private Long upperCatCode;
    private CategoryStatus status;

    private Integer catLevel;
}
