package jp.my.spring.rest_open_api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.my.spring.rest_open_api.domain.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
