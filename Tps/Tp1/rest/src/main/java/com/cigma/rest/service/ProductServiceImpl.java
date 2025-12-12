package com.cigma.rest.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.cigma.rest.model.Product;

@Service
public class ProductServiceImpl implements IProductService{
    private static List<Product> productRepo = new ArrayList<>();
    static {
        productRepo.add(new Product(1l,"PC PORTABLE HP"));
        productRepo.add(new Product(2l,"TV LG 32p"));
        productRepo.add(new Product(3l,"TV Sony 49p"));
        productRepo.add(new Product(4l,"Camera Sony"));
    }

    @Override
    public Product getById(Long id) {
        if (productRepo == null || productRepo.isEmpty()) return null;
        for (Product product : productRepo) {
            if (product.getId().equals(id)) return product;
        }
        return null;
    }

    @Override
    public List<Product> getAll() {
        return productRepo;
    }

    @Override
    public void update(Long id, Product p){
        Product product = getById(id);
        if (product == null) return;
        productRepo.remove(product);
        p.setId(id);
        productRepo.add(p);
    }

    @Override
    public void delete(Long id){
        Product product = getById(id);
        if (product == null) return;
        productRepo.remove(product);
    }

    @Override
    public void create(Product product) {
        productRepo.add(product);
    }
}
