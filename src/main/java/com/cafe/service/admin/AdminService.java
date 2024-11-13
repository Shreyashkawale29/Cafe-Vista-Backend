package com.cafe.service.admin;

import com.cafe.dto.CategoryDto;
import com.cafe.dto.ProductDto;
import com.cafe.dto.ReservationDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdminService {
    CategoryDto postCategory(CategoryDto categoryDto) throws IOException;

    List<CategoryDto> getAllCategories();

    List<CategoryDto> getAllCategoriesByTitle(String title);

    ProductDto postProduct(Long categoryId, ProductDto productDto) throws IOException;

    List<ProductDto> getAllProductsByCategory(Long categoryId);

    List<ProductDto> getProductsByCategoryAndTitle(Long categoryId, String title);

    void deleteProduct(Long productId);

    ProductDto getProductsById(Long productId);

    ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException;

    List<ReservationDto> getReservations();

    ReservationDto changeReservationstatus(Long reservationId, String status);


}
