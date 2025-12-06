package com.olimpo.olimpo.dtos;

import lombok.Data;

@Data
public class AddToCartRequest {
    private Long basketId;
    private int cantidad;
}
