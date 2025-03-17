package com.artglimpse.seller.service;

import com.artglimpse.seller.dto.AddProductDTO;
import com.artglimpse.seller.model.AddProduct;
import com.artglimpse.seller.repository.AddProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddProductService {

    private final AddProductRepository addProductRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public AddProductService(AddProductRepository addProductRepository) {
        this.addProductRepository = addProductRepository;
    }

    public AddProduct addProduct(AddProductDTO addProductDTO, MultipartFile[] images) {
        try {
            List<String> imagePaths = new ArrayList<>();
            if (images != null) {
                for (MultipartFile file : images) {
                    if (!file.isEmpty()) {
                        String fileName = System.currentTimeMillis() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
                        Path fileStorage = Paths.get(uploadPath, fileName);
                        Files.createDirectories(fileStorage.getParent());
                        Files.copy(file.getInputStream(), fileStorage, StandardCopyOption.REPLACE_EXISTING);
                        imagePaths.add(fileName);
                    }
                }
            }
            
            AddProduct product = new AddProduct();
            product.setName(addProductDTO.getName());
            product.setDescription(addProductDTO.getDescription());
            product.setPrice(addProductDTO.getPrice());
            product.setCurrency(addProductDTO.getCurrency());
            product.setStock(addProductDTO.getStock());
            product.setCategory(addProductDTO.getCategory());
            product.setDiscount(addProductDTO.getDiscount());
            product.setPercentageDiscount(addProductDTO.getPercentageDiscount());
            product.setValidUntilDiscount(addProductDTO.getValidUntilDiscount());
            product.setProcessingTime(addProductDTO.getProcessingTime());
            product.setShippingTime(addProductDTO.getShippingTime());
            product.setShippingCost(addProductDTO.getShippingCost());
            product.setEstimatedDelivery(addProductDTO.getEstimatedDelivery());
            product.setMaterialsMade(addProductDTO.getMaterialsMade());
            product.setTags(addProductDTO.getTags());
            product.setImages(imagePaths);
            
            return addProductRepository.save(product);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to store files", e);
        }
    }

    public List<AddProduct> getAllProducts() {
        return addProductRepository.findAll();
    }

    public Optional<AddProduct> getProductById(String id) {
        return addProductRepository.findById(id);
    }

    public AddProduct updateProduct(String id, AddProduct product) {
        product.setId(id);
        return addProductRepository.save(product);
    }

    public void deleteProduct(String id) {
        addProductRepository.deleteById(id);
    }
}
