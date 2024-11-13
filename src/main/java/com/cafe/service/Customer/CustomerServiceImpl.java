package com.cafe.service.Customer;

import com.cafe.dto.CategoryDto;
import com.cafe.dto.ProductDto;
import com.cafe.dto.ReservationDto;
import com.cafe.entity.Category;
import com.cafe.entity.Product;
import com.cafe.entity.Reservation;
import com.cafe.entity.User;
import com.cafe.enums.ReservationStatus;
import com.cafe.repository.CategoryRepository;
import com.cafe.repository.ProductRepository;
import com.cafe.repository.ReservationRepository;
import com.cafe.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class CustomerServiceImpl implements CustomerService{

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ReservationRepository reservationRepository;

    private final UserRepository userRepository;

    public CustomerServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ReservationRepository reservationRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(Category::getcategoryDto).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getCategoriesByName(String title) {
        return categoryRepository.findAllByNameContaining(title).stream().map(Category::getcategoryDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategory(Long categoryId) {
        return productRepository.findAllByCategoryId(categoryId).stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndTitle(Long categoryId, String title) {
        return productRepository.findAllByCategoryIdAndNameContaining(categoryId,title).stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public ReservationDto postReservation(ReservationDto reservationDto) {
        Optional<User> optionalUser = userRepository.findById(reservationDto.getCustomerId());
        if (optionalUser.isPresent()) {
            Reservation reservation = new Reservation();
            reservation.setTableType(reservationDto.getTableType());
            reservation.setDateTime(reservationDto.getDateTime());
            reservation.setDescription(reservationDto.getDescription());
            reservation.setUser(optionalUser.get());
            reservation.setReservationStatus(ReservationStatus.PENDING);

            Reservation postedReservation = reservationRepository.save(reservation);

            // Fix: Set the id from the saved reservation
            ReservationDto postedReservationDto = new ReservationDto();
            postedReservationDto.setId(postedReservation.getId()); // Fixed id assignment
            return postedReservationDto;
        }
        return null;
    }


    @Override
    public List<ReservationDto> getReservationsByUser(Long customerId) {
        return reservationRepository.findAllByUserId(customerId).stream().map(Reservation::getReservationDto).collect(Collectors.toList());
    }


}
