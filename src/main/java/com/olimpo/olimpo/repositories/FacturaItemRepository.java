package com.olimpo.olimpo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olimpo.olimpo.entities.FacturaItemEntity;

public interface FacturaItemRepository extends JpaRepository<FacturaItemEntity, Long> {

    List<FacturaItemEntity> findByFacturaId(Long facturaId);
}
