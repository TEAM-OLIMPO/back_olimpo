package com.olimpo.olimpo.repositories;

import com.olimpo.olimpo.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
