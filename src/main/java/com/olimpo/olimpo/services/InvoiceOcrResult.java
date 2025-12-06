package com.olimpo.olimpo.services;

import com.olimpo.olimpo.entities.ItemType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceOcrResult {

    public static class Item {
        public String code;
        public String description;
        public Integer quantity;
        public BigDecimal unitPrice;
        public BigDecimal totalPrice;
        public ItemType type;
    }

    public String patientId;
    public LocalDate invoiceDate;
    public String providerName;
    public String providerNit;
    public BigDecimal totalAmount;
    public List<Item> items = new ArrayList<>();
}
