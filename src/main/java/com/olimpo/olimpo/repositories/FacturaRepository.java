package com.olimpo.olimpo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olimpo.olimpo.entities.FacturaEntity;

public interface FacturaRepository extends JpaRepository<FacturaEntity, Long> {
}
