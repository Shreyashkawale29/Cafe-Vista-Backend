package com.cafe.controller;

import com.cafe.dto.CategoryDto;
import com.cafe.dto.ProductDto;
import com.cafe.dto.ReservationDto;
import com.cafe.repository.ProductRepository;
import com.cafe.service.admin.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {



    private final AdminService adminService;

    public AdminController(ProductRepository productRepository, AdminService adminService) {

        this.adminService = adminService;
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryDto> postCategory(@ModelAttribute CategoryDto categoryDto) throws IOException {

       CategoryDto createdcategoryDto= adminService.postCategory(categoryDto);
       if(createdcategoryDto == null) return ResponseEntity.notFound().build();

       return ResponseEntity.ok(createdcategoryDto);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categoryDtosList =adminService.getAllCategories();
        if(categoryDtosList == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(categoryDtosList);

    }

    @GetMapping("/categories/{title}")
    public ResponseEntity<List<CategoryDto>> getAllCategoriesByTitle(@PathVariable String title){
        List<CategoryDto> categoryDtosList =adminService.getAllCategoriesByTitle(title);
        if(categoryDtosList == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(categoryDtosList);

    }

    // Product Optiorations


    @PostMapping("/{categoryId}/product")
    public ResponseEntity<?> postProduct(@PathVariable Long categoryId,@ModelAttribute ProductDto productDto )throws IOException {

        ProductDto createdProductDto= adminService.postProduct(categoryId, productDto);
        if(createdProductDto == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDto);
    }


    @GetMapping("/{categoryId}/products")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategory(@PathVariable Long categoryId){
        List<ProductDto> productDtoList =adminService.getAllProductsByCategory(categoryId);
        if(productDtoList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDtoList);
    }

    @GetMapping("/{categoryId}/product/{title}")
    public ResponseEntity<List<ProductDto>> getProductsByCategoryAndTitle(@PathVariable Long categoryId, @PathVariable String title){
        List<ProductDto> productDtoList=adminService.getProductsByCategoryAndTitle(categoryId,title);
        if(productDtoList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDtoList);

    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
        adminService.deleteProduct(productId);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("product/{productId}")
    public ResponseEntity<ProductDto> getProductsById(@PathVariable Long productId){
        ProductDto productDto=adminService.getProductsById(productId);
        if(productDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDto);

    }

    @PutMapping("product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @ModelAttribute ProductDto productDto) throws IOException {

        ProductDto updatedproductDto= adminService.updateProduct(productId, productDto);
        if(updatedproductDto == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");


        return ResponseEntity.status(HttpStatus.OK).body(updatedproductDto);
    }

    @GetMapping("/reservations")
    public ResponseEntity <List<ReservationDto>> getReservations(){
        List<ReservationDto> reservationDtoList=adminService.getReservations();
        if(reservationDtoList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservationDtoList);

    }

    @GetMapping("/reservation/{reservationId}/{status}")
    public ResponseEntity <ReservationDto> changeReservationstatus(@PathVariable Long reservationId,@PathVariable String status ){
        ReservationDto updatedreservationDto=adminService.changeReservationstatus(reservationId, status);
        if(updatedreservationDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedreservationDto);

    }


}
