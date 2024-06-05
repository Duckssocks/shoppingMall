package com.example.shopingmall.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Item {
    @NotEmpty
    private String itemName;
    @NotEmpty
    private Integer price;
    @NotEmpty
    private String itemDescription;
    private Long itemId;
    @NotEmpty
    private String sellerName;
    @NotEmpty
    private String sellerId;
}
