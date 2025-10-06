package com.example.tm.repositories;

import com.example.tm.domain.Feature;
import com.example.tm.domain.TenantPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {

    List<Feature> findByIdIn(List<Long> ids);
}
