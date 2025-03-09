package com.artglimpse.buyer.dto.product;

import com.artglimpse.buyer.model.product.Product;
import com.artglimpse.buyer.model.profile.BuyerProfile;
import lombok.Data;
import java.util.List;

@Data
public class WishlistResponse {
    private BuyerProfile user;
    private List<Product> products;
}
