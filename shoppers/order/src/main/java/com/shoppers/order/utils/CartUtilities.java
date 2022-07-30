package com.shoppers.order.utils;

import java.math.BigDecimal;

import com.shoppers.order.model.Product;

public class CartUtilities {

    public static BigDecimal getSubTotalForItem(Product product, int quantity){
       return (product.getPrice()).multiply(BigDecimal.valueOf(quantity));
    }
}
