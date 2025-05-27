package com.test.shoebox.repository;

import com.test.shoebox.dto.ProductSearchCondition;
import com.test.shoebox.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryImpl {

    @PersistenceContext
    private EntityManager em;

    public List<Product> searchByCondition(ProductSearchCondition cond) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM Product p WHERE 1=1");

        if (cond.getRegistrationDateStart() != null) {
            jpql.append(" AND p.productRegisterDate >= :start");
        }
        if (cond.getRegistrationDateEnd() != null) {
            jpql.append(" AND p.productRegisterDate <= :end");
        }
        if (cond.getBrandName() != null && !cond.getBrandName().isBlank()) {
            jpql.append(" AND p.brand.brandName = :brandName");
        }
        if (cond.getCategoryName() != null && !cond.getCategoryName().isBlank()) {
            jpql.append(" AND p.categories.categoriesName = :categoryName");
        }
        if (cond.getSearchKeyword() != null && !cond.getSearchKeyword().isBlank()) {
            switch (cond.getSearchType()) {
                case "productName" -> jpql.append(" AND p.productName LIKE :keyword");
                case "productCode" -> jpql.append(" AND p.productCode LIKE :keyword");
                case "modelNumber" -> jpql.append(" AND p.modelNumber LIKE :keyword");
            }
        }

        // 정렬
        jpql.append(" ORDER BY ");
        switch (cond.getSort()) {
            case "price_asc" -> jpql.append("p.productPrice ASC");
            case "price_desc" -> jpql.append("p.productPrice DESC");
            case "name_asc" -> jpql.append("p.productName ASC");
            case "createdAt_asc" -> jpql.append("p.productRegisterDate ASC");
            default -> jpql.append("p.productRegisterDate DESC");
        }

        TypedQuery<Product> query = em.createQuery(jpql.toString(), Product.class);

        if (cond.getRegistrationDateStart() != null) {
            query.setParameter("start", cond.getRegistrationDateStart().atStartOfDay());
        }
        if (cond.getRegistrationDateEnd() != null) {
            query.setParameter("end", cond.getRegistrationDateEnd().atTime(23, 59, 59));
        }
        if (cond.getBrandName() != null && !cond.getBrandName().isBlank()) {
            query.setParameter("brandName", cond.getBrandName());
        }
        if (cond.getCategoryName() != null && !cond.getCategoryName().isBlank()) {
            query.setParameter("categoryName", cond.getCategoryName());
        }
        if (cond.getSearchKeyword() != null && !cond.getSearchKeyword().isBlank()) {
            query.setParameter("keyword", "%" + cond.getSearchKeyword() + "%");
        }

        return query.getResultList();
    }
}
