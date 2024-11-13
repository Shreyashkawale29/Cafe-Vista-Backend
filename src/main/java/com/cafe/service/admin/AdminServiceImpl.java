package com.cafe.service.admin;

import com.cafe.dto.CategoryDto;
import com.cafe.dto.ProductDto;
import com.cafe.dto.ReservationDto;
import com.cafe.entity.Category;
import com.cafe.entity.Product;
import com.cafe.entity.Reservation;
import com.cafe.enums.ReservationStatus;
import com.cafe.repository.CategoryRepository;
import com.cafe.repository.ProductRepository;
import com.cafe.repository.ReservationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ReservationRepository reservationRepository;

    public AdminServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ReservationRepository reservationRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public CategoryDto postCategory(CategoryDto categoryDto) throws IOException {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setImg(categoryDto.getImg().getBytes());
        Category createdCategory = categoryRepository.save(category);

        // Map all fields of the created category to CategoryDto
        CategoryDto createdCategoryDto = new CategoryDto();
        BeanUtils.copyProperties(createdCategory, createdCategoryDto);
        return createdCategoryDto;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(Category::getcategoryDto).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getAllCategoriesByTitle(String title) {
        return categoryRepository.findAllByNameContaining(title).stream().map(Category::getcategoryDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto postProduct(Long categoryId, ProductDto productDto) throws IOException {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Product product = new Product();
            BeanUtils.copyProperties(productDto, product);

            product.setImg(productDto.getImg().getBytes());
            product.setCategory(optionalCategory.get());
            Product createdProduct = productRepository.save(product);

            // Map all fields of the created product to ProductDto
            ProductDto createdProductDto = new ProductDto();
            createdProductDto.setId(createdProduct.getId());
//            BeanUtils.copyProperties(createdProduct, createdProductDto);
            return createdProductDto;
        }
        throw new IllegalArgumentException("Category with id " + categoryId + " not found");
    }

    @Override
    public List<ProductDto> getAllProductsByCategory(Long categoryId) {
        return productRepository.findAllByCategoryId(categoryId).stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndTitle(Long categoryId, String title) {
        return productRepository.findAllByCategoryIdAndNameContaining(categoryId, title).stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
        } else {
            throw new IllegalArgumentException("Product with id: " + productId + " not found");
        }
    }

    @Override
    public ProductDto getProductsById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.map(Product::getProductDto).orElseThrow(() -> new IllegalArgumentException("Product with id " + productId + " not found"));
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            if (productDto.getImg() != null) {
                product.setImg(productDto.getImg().getBytes());
            }
            Product updatedProduct = productRepository.save(product);

            // Map all fields of the updated product to ProductDto
            ProductDto updatedProductDto = new ProductDto();
            BeanUtils.copyProperties(updatedProduct, updatedProductDto);
            return updatedProductDto;
        }
        throw new IllegalArgumentException("Product with id " + productId + " not found");
    }

    @Override
    public List<ReservationDto> getReservations() {
        return reservationRepository.findAll().stream().map(Reservation::getReservationDto).collect(Collectors.toList());
    }

    @Override
    public ReservationDto changeReservationstatus(Long reservationId, String status) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if (optionalReservation.isPresent()) {
            Reservation existingReservation = optionalReservation.get();
            existingReservation.setReservationStatus("Approve".equalsIgnoreCase(status) ? ReservationStatus.APPROVED : ReservationStatus.DISAPPROVED);
            Reservation updatedReservation = reservationRepository.save(existingReservation);

            // Map all fields of the updated reservation to ReservationDto
            ReservationDto updatedReservationDto = new ReservationDto();
            BeanUtils.copyProperties(updatedReservation, updatedReservationDto);
            return updatedReservationDto;
        }
        throw new IllegalArgumentException("Reservation with id " + reservationId + " not found");
    }
}
