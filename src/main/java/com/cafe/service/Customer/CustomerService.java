package com.cafe.service.Customer;

import com.cafe.dto.CategoryDto;
import com.cafe.dto.ProductDto;
import com.cafe.dto.ReservationDto;

import java.util.List;

public interface CustomerService {
    List<CategoryDto> getAllCategories();

    List<CategoryDto> getCategoriesByName(String title);

    List<ProductDto> getProductsByCategory(Long categoryId);



    List<ProductDto> getProductsByCategoryAndTitle(Long categoryId, String title);


    ReservationDto postReservation(ReservationDto reservationDto);

    List<ReservationDto> getReservationsByUser(Long customerId);
}
