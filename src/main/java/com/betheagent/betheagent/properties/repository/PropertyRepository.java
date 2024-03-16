package com.betheagent.betheagent.properties.repository;

import com.betheagent.betheagent.properties.entity.PropertyInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.stream.Stream;

public interface PropertyRepository extends JpaRepository<PropertyInstance, String>, JpaSpecificationExecutor<PropertyInstance> {
    @Query("select p from PropertyInstance p where p.authorId = ?1")
    Stream<PropertyInstance> findAllPropertiesByUserId(String authorId);

}
