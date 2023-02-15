package co.kr.category.controller;

import co.kr.category.dto.CategoryRequestDto;
import co.kr.category.http.HttpStatus;
import co.kr.category.http.Response;
import co.kr.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RequestMapping("/categories")
@RestController
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping("")
    private ResponseEntity<Response> selectCategories(@RequestParam(required = false) Long upperCatCode){

        if (Optional.ofNullable(upperCatCode).orElse(0L) != 0){
            return ResponseEntity.ok().body(new Response(HttpStatus.NO_ERROR,categoryService.getChildCategoryList(upperCatCode)));
        } else {
            return ResponseEntity.ok().body(new Response(HttpStatus.NO_ERROR,categoryService.selectCategories()));

        }

    }

    @GetMapping("/{id}")
    private ResponseEntity<Response> detailCategory(@RequestParam(required = false) Long id){
        return ResponseEntity.ok().body(new Response(HttpStatus.NO_ERROR,categoryService.detailCategory(id)));
    }

    @PostMapping("")
    private ResponseEntity<Response> addCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto){
        categoryService.addCategory(categoryRequestDto);
        return ResponseEntity.ok().body(new Response(HttpStatus.NO_ERROR));
    }

    @PutMapping("/{id}")
    private ResponseEntity<Response> updateCategory(@RequestBody @Valid CategoryRequestDto categoryRequestDto,@PathVariable Long id){
        categoryService.updateCategory(categoryRequestDto,id);
        return ResponseEntity.ok().body(new Response(HttpStatus.NO_ERROR));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Response> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().body(new Response(HttpStatus.NO_ERROR));
    }

}