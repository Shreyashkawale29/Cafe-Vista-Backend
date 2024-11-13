package com.cafe.repository;

import com.cafe.dto.ProductDto;
import com.cafe.entity.Product;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryId(Long categoryId);

    List<Product> findAllByCategoryIdAndNameContaining(Long categoryId, String title);
}
