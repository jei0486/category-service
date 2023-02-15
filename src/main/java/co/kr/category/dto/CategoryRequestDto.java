package co.kr.category.dto;

import co.kr.category.enums.CategoryStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Getter
@ToString
public class CategoryRequestDto {

    @NotBlank(message = "카테고리명을 입력해주세요.")
    @Size(max = 50, message = "올바른 카테고리명 값을 입력했는지 확인해주세요.")
    private String catName;

    @NotNull(message = "카테고리 상태를 입력해주세요")
    private CategoryStatus status;

    @NotNull(message = "카테고리 레벨 값을 입력해주세요")
    private Integer catLevel;

    private Long upperCatCode;

}