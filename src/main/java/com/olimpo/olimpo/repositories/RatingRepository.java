package com.olimpo.olimpo.repositories;

import com.olimpo.olimpo.models.Rating;
import com.olimpo.olimpo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByVendorOrderByFechaCreacionDesc(User vendor);
}
