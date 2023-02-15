package co.kr.category.service;

import co.kr.category.dto.CategoryRequestDto;
import co.kr.category.dto.CategoryResponseDto;
import co.kr.category.domain.entity.Category;
import co.kr.category.enums.CategoryStatus;
import co.kr.category.error.exception.CategoryNotFoundException;
import co.kr.category.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponseDto> selectCategories() {
        List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<>();
        List<Category> categoryList = categoryRepository.findAllByStatus(CategoryStatus.ACTIVE);
        CategoryResponseDto categoryResponseDto = null;

        for (Category category : categoryList){
            categoryResponseDto = categoryResponseDto.builder()
                    .id(category.getId())
                    .catLevel(category.getCatLevel())
                    .catName(category.getCatName())
                    .upperCatCode(category.getUpperCatCode())
                    .status(category.getStatus())
                    .build();
            categoryResponseDtoList.add(categoryResponseDto);
        }
        return categoryResponseDtoList;
    }

    @Override
    public List<CategoryResponseDto> getChildCategoryList(Long upperCatCode) {

        List<Category> secondCategoryList = categoryRepository.findAllByUpperCatCodeAndStatus(upperCatCode,CategoryStatus.ACTIVE);
        List<CategoryResponseDto> secondCategoryDtoList = new ArrayList<>();
        CategoryResponseDto categoryResponseDto = null;

        for (Category category : secondCategoryList){
            categoryResponseDto = categoryResponseDto.builder()
                    .id(category.getId())
                    .catLevel(category.getCatLevel())
                    .catName(category.getCatName())
                    .upperCatCode(category.getUpperCatCode())
                    .status(category.getStatus())
                    .build();
            secondCategoryDtoList.add(categoryResponseDto);
        }
        return secondCategoryDtoList;
    }

    @Override
    public CategoryResponseDto detailCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()
                -> new CategoryNotFoundException("존재하지 않는 카테고리 입니다."));
        CategoryResponseDto categoryResponseDto = CategoryResponseDto.builder()
                .id(category.getId())
                .catLevel(category.getCatLevel())
                .catName(category.getCatName())
                .upperCatCode(category.getUpperCatCode())
                .status(category.getStatus())
                .build();
        return categoryResponseDto;
    }


    @Transactional
    @Override
    public void addCategory(CategoryRequestDto categoryRequestDto) {

        // 상위 카테고리 코드(upperCatCode) 가 DTO 에 있다면 DB 에 존재하는지 Check
        if (Optional.ofNullable(categoryRequestDto.getUpperCatCode()).orElse(0L) != 0){
            Category category = categoryRepository.findById(categoryRequestDto.getUpperCatCode()).orElseThrow(()
                    -> new CategoryNotFoundException("존재하지 않는 상위 카테고리 입니다."));
        }

        // 카테고리 저장
        categoryRepository.save(Category.builder()
                .catLevel(categoryRequestDto.getCatLevel())
                .catName(categoryRequestDto.getCatName())
                .upperCatCode(categoryRequestDto.getUpperCatCode())
                .status(categoryRequestDto.getStatus())
                .build());

    }

    /**
     * 카테고리 수정
     * @param categoryRequestDto
     * @param id
     */
    @Transactional
    @Override
    public void updateCategory(CategoryRequestDto categoryRequestDto, Long id) {

        Category category = categoryRepository.findById(id).orElseThrow(()
                -> new CategoryNotFoundException("존재하지 않는 카테고리 입니다."));

        category.setCatName(categoryRequestDto.getCatName());
        category.setStatus(categoryRequestDto.getStatus());
        category.setUpperCatCode(categoryRequestDto.getUpperCatCode());
        categoryRepository.save(category);


    }

    /**
     * 카테고리 삭제
     * @param id
     */
    @Transactional
    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()
                -> new CategoryNotFoundException("존재하지 않는 카테고리 입니다."));

        categoryRepository.delete(category);

    }

}