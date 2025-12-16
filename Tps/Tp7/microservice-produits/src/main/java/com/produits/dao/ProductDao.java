package com.produits.dao;

import com.produits.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

    List<Product> findByTitre(String titre);

    List<Product> findByTitreIgnoreCase(String titre);

    List<Product> findByTitreContaining(String keyword);

    List<Product> findByTitreContainingIgnoreCase(String keyword);

    List<Product> findByPrix(Double prix);

    List<Product> findByPrixGreaterThan(Double prix);

    List<Product> findByPrixLessThan(Double prix);

    List<Product> findByPrixBetween(Double prixMin, Double prixMax);

    List<Product> findByDescriptionContaining(String keyword);

    List<Product> findAllByOrderByPrixAsc();

    List<Product> findAllByOrderByPrixDesc();

    List<Product> findAllByOrderByTitreAsc();

    Long countByPrixGreaterThan(Double prix);

    void deleteByTitre(String titre);

    @Query("SELECT p FROM Product p WHERE LOWER(p.titre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProducts(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE p.prix BETWEEN :minPrice AND :maxPrice ORDER BY p.prix ASC")
    List<Product> findProductsByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    @Query("SELECT p FROM Product p ORDER BY p.prix DESC")
    List<Product> findTopExpensiveProducts(@Param("limit") int limit);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.prix < :maxPrice")
    Long countProductsCheaperThan(@Param("maxPrice") Double maxPrice);

    @Query("SELECT AVG(p.prix) FROM Product p")
    Double getAveragePrice();

    @Query("SELECT MAX(p.prix) FROM Product p")
    Double getMaxPrice();

    @Query("SELECT MIN(p.prix) FROM Product p")
    Double getMinPrice();

    Optional<Product> findFirstByOrderByPrixDesc();

    Optional<Product> findFirstByOrderByPrixAsc();
}