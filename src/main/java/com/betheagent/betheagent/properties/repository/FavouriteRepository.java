package com.betheagent.betheagent.properties.repository;

import com.betheagent.betheagent.properties.entity.Favourites;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRepository extends JpaRepository<Favourites, String> {
}
