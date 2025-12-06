package com.olimpo.olimpo.dtos;

import lombok.Data;

@Data
public class RatingRequest {
    private Long purchaseId;
    private int estrellas;
    private String comentario;
}
