package com.betheagent.betheagent.properties.repository;

import com.betheagent.betheagent.properties.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface PropertyRepository extends JpaRepository<PropertyEntity, String> {
    @Query("select p from PropertyEntity p where p.authorId = ?1")
    Stream<PropertyEntity> findAllPropertiesByUserId(String authorId);


}
