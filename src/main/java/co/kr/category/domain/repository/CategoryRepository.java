package co.kr.category.domain.repository;

import co.kr.category.domain.entity.Category;
import co.kr.category.enums.CategoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByUpperCatCodeAndStatus(Long upperCatCode,CategoryStatus status);

    List<Category> findAllByStatus(CategoryStatus status);

    Category findByCatLevelAndStatus(Integer level, CategoryStatus status);

}