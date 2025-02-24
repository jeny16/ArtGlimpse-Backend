package com.artglimpse.dto.product;

import com.artglimpse.model.product.Product;
import com.artglimpse.model.auth.User;
import lombok.Data;
import java.util.List;

@Data
public class WishlistResponse {
    private User user;
    private List<Product> products;
}
