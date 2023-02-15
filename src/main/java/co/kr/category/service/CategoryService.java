package co.kr.category.service;

import co.kr.category.dto.CategoryRequestDto;
import co.kr.category.dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {

    List<CategoryResponseDto> selectCategories(Long upperCatCode);

    List<CategoryResponseDto> getChildCategoryList(Long upperCatCode);

    void addCategory(CategoryRequestDto categoryRequestDto);

    void saveCategory(CategoryRequestDto categoryRequestDto);

    void updateCategory(CategoryRequestDto categoryRequestDto,Long id);

    void deleteCategory(Long id);

}