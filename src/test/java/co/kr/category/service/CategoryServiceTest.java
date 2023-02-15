package co.kr.category.service;

import co.kr.category.domain.repository.CategoryRepository;
import co.kr.category.dto.CategoryRequestDto;
import co.kr.category.dto.CategoryResponseDto;
import co.kr.category.enums.CategoryStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


import java.util.List;
@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryServiceImpl categoryService;


    @DisplayName("상위 카테고리를 지정하지 않을 시, 전체 카테고리를 반환")
    @Test
    void getALLCategory(){

        categoryService.addCategory(makeFirstCategory());
        categoryService.addCategory(makeChildCategoryOne());
        categoryService.addCategory(makeChildCategoryTwo());
        categoryService.addCategory(makeChildCategoryThree());

        // when
        List<CategoryResponseDto> categoryResponseDtoList = categoryService.selectCategories();

        // then
        assertThat(categoryResponseDtoList.size()).isEqualTo(3);
    }


    @DisplayName("상위 카테고리를 이용해, 해당 카테고리의 하위의 모든 카테고리를 조회")
    @Test
    void getChildCategory(){

        // when
        List<CategoryResponseDto> categoryResponseDtoList = categoryService.getChildCategoryList(1L);

        // then
        assertThat(categoryResponseDtoList.size()).isEqualTo(2);
    }

    CategoryRequestDto makeFirstCategory(){
        return CategoryRequestDto.builder()
                .catLevel(1)
                .catName("상의")
                .status(CategoryStatus.ACTIVE)
                .build();
    }

    CategoryRequestDto makeChildCategoryOne(){
        return CategoryRequestDto.builder()
                .catLevel(2)
                .upperCatCode(1L)
                .catName("니트")
                .status(CategoryStatus.ACTIVE)
                .build();
    }

    CategoryRequestDto makeChildCategoryTwo(){
        return CategoryRequestDto.builder()
                .catLevel(2)
                .upperCatCode(1L)
                .catName("셔츠")
                .status(CategoryStatus.ACTIVE)
                .build();
    }

    CategoryRequestDto makeChildCategoryThree(){
        return CategoryRequestDto.builder()
                .catLevel(2)
                .upperCatCode(1L)
                .catName("스웨터")
                .status(CategoryStatus.READY)
                .build();
    }



}
