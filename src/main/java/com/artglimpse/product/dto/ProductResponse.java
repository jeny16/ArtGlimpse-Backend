// package com.artglimpse.product.dto;

// import com.artglimpse.product.model.Product;
// import com.artglimpse.seller.model.Seller;
// import java.time.LocalDate;
// import java.util.List;

// public class ProductResponse {
//     private String id;
//     private String categories;
//     private String name;
//     private String description;
//     private double price;
//     private String currency;
//     private int stock;
//     private List<String> images;
//     private boolean discount;
//     private LocalDate valid_Until_Discount;
//     private int percentage_Discount;
//     private Seller seller; // Full seller details
//     private String processing_Time;
//     private String shipping_Time;
//     private double shipping_Cost;
//     private String estimated_Delivery;
//     private List<String> countries_Available;
//     private List<String> materials_Made;
//     private List<String> tags;

//     public ProductResponse(Product product, Seller seller) {
//         this.id = product.getId();
//         this.categories = product.getCategories();
//         this.name = product.getName();
//         this.description = product.getDescription();
//         this.price = product.getPrice();
//         this.currency = product.getCurrency();
//         this.stock = product.getStock();
//         this.images = product.getImages();
//         this.discount = product.isDiscount();
//         this.valid_Until_Discount = product.getValid_Until_Discount();
//         this.percentage_Discount = product.getPercentage_Discount();
//         this.seller = seller;
//         this.processing_Time = product.getProcessing_Time();
//         this.shipping_Time = product.getShipping_Time();
//         this.shipping_Cost = product.getShipping_Cost();
//         this.estimated_Delivery = product.getEstimated_Delivery();
//         this.countries_Available = product.getCountries_Available();
//         this.materials_Made = product.getMaterials_Made();
//         this.tags = product.getTags();
//     }

//     // Getters and setters (generated)
//     public String getId() {
//         return id;
//     }

//     public void setId(String id) {
//         this.id = id;
//     }

//     public String getCategories() {
//         return categories;
//     }

//     public void setCategories(String categories) {
//         this.categories = categories;
//     }

//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public String getDescription() {
//         return description;
//     }

//     public void setDescription(String description) {
//         this.description = description;
//     }

//     public double getPrice() {
//         return price;
//     }

//     public void setPrice(double price) {
//         this.price = price;
//     }

//     public String getCurrency() {
//         return currency;
//     }

//     public void setCurrency(String currency) {
//         this.currency = currency;
//     }

//     public int getStock() {
//         return stock;
//     }

//     public void setStock(int stock) {
//         this.stock = stock;
//     }

//     public List<String> getImages() {
//         return images;
//     }

//     public void setImages(List<String> images) {
//         this.images = images;
//     }

//     public boolean isDiscount() {
//         return discount;
//     }

//     public void setDiscount(boolean discount) {
//         this.discount = discount;
//     }

//     public LocalDate getValid_Until_Discount() {
//         return valid_Until_Discount;
//     }

//     public void setValid_Until_Discount(LocalDate valid_Until_Discount) {
//         this.valid_Until_Discount = valid_Until_Discount;
//     }

//     public int getPercentage_Discount() {
//         return percentage_Discount;
//     }

//     public void setPercentage_Discount(int percentage_Discount) {
//         this.percentage_Discount = percentage_Discount;
//     }

//     public Seller getSeller() {
//         return seller;
//     }

//     public void setSeller(Seller seller) {
//         this.seller = seller;
//     }

//     public String getProcessing_Time() {
//         return processing_Time;
//     }

//     public void setProcessing_Time(String processing_Time) {
//         this.processing_Time = processing_Time;
//     }

//     public String getShipping_Time() {
//         return shipping_Time;
//     }

//     public void setShipping_Time(String shipping_Time) {
//         this.shipping_Time = shipping_Time;
//     }

//     public double getShipping_Cost() {
//         return shipping_Cost;
//     }

//     public void setShipping_Cost(double shipping_Cost) {
//         this.shipping_Cost = shipping_Cost;
//     }

//     public String getEstimated_Delivery() {
//         return estimated_Delivery;
//     }

//     public void setEstimated_Delivery(String estimated_Delivery) {
//         this.estimated_Delivery = estimated_Delivery;
//     }

//     public List<String> getCountries_Available() {
//         return countries_Available;
//     }

//     public void setCountries_Available(List<String> countries_Available) {
//         this.countries_Available = countries_Available;
//     }

//     public List<String> getMaterials_Made() {
//         return materials_Made;
//     }

//     public void setMaterials_Made(List<String> materials_Made) {
//         this.materials_Made = materials_Made;
//     }

//     public List<String> getTags() {
//         return tags;
//     }

//     public void setTags(List<String> tags) {
//         this.tags = tags;
//     }
// }

package com.artglimpse.product.dto;

import com.artglimpse.product.model.Product;
import com.artglimpse.seller.dto.SellerResponse;
import java.time.LocalDate;
import java.util.List;

public class ProductResponse {
    private String id;
    private String categories;
    private String name;
    private String description;
    private double price;
    private String currency;
    private int stock;
    private List<String> images;
    private boolean discount;
    private LocalDate valid_Until_Discount;
    private int percentage_Discount;
    private SellerResponse seller; // Now full seller details via SellerResponse
    private String processing_Time;
    private String shipping_Time;
    private double shipping_Cost;
    private String estimated_Delivery;
    private List<String> countries_Available;
    private List<String> materials_Made;
    private List<String> tags;

    public ProductResponse(Product product, SellerResponse seller) {
        this.id = product.getId();
        this.categories = product.getCategories();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.currency = product.getCurrency();
        this.stock = product.getStock();
        this.images = product.getImages();
        this.discount = product.isDiscount();
        this.valid_Until_Discount = product.getValid_Until_Discount();
        this.percentage_Discount = product.getPercentage_Discount();
        this.seller = seller;
        this.processing_Time = product.getProcessing_Time();
        this.shipping_Time = product.getShipping_Time();
        this.shipping_Cost = product.getShipping_Cost();
        this.estimated_Delivery = product.getEstimated_Delivery();
        this.countries_Available = product.getCountries_Available();
        this.materials_Made = product.getMaterials_Made();
        this.tags = product.getTags();
    }

    // Getters and setters (generate using your IDE)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public LocalDate getValid_Until_Discount() {
        return valid_Until_Discount;
    }

    public void setValid_Until_Discount(LocalDate valid_Until_Discount) {
        this.valid_Until_Discount = valid_Until_Discount;
    }

    public int getPercentage_Discount() {
        return percentage_Discount;
    }

    public void setPercentage_Discount(int percentage_Discount) {
        this.percentage_Discount = percentage_Discount;
    }

    public SellerResponse getSeller() {
        return seller;
    }

    public void setSeller(SellerResponse seller) {
        this.seller = seller;
    }

    public String getProcessing_Time() {
        return processing_Time;
    }

    public void setProcessing_Time(String processing_Time) {
        this.processing_Time = processing_Time;
    }

    public String getShipping_Time() {
        return shipping_Time;
    }

    public void setShipping_Time(String shipping_Time) {
        this.shipping_Time = shipping_Time;
    }

    public double getShipping_Cost() {
        return shipping_Cost;
    }

    public void setShipping_Cost(double shipping_Cost) {
        this.shipping_Cost = shipping_Cost;
    }

    public String getEstimated_Delivery() {
        return estimated_Delivery;
    }

    public void setEstimated_Delivery(String estimated_Delivery) {
        this.estimated_Delivery = estimated_Delivery;
    }

    public List<String> getCountries_Available() {
        return countries_Available;
    }

    public void setCountries_Available(List<String> countries_Available) {
        this.countries_Available = countries_Available;
    }

    public List<String> getMaterials_Made() {
        return materials_Made;
    }

    public void setMaterials_Made(List<String> materials_Made) {
        this.materials_Made = materials_Made;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
