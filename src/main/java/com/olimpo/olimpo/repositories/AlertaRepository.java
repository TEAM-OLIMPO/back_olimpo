package com.olimpo.olimpo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olimpo.olimpo.entities.AlertaEntity;

public interface AlertaRepository extends JpaRepository<AlertaEntity, Long> {

    List<AlertaEntity> findByFacturaId(Long facturaId);
}
