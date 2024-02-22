package com.example.learningportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.learningportal.entities.FavouriteEntity;

@Repository
public interface FavouriteRepository extends JpaRepository<FavouriteEntity, Long> {
	/*
		@Query(value = "SELECT f.*,u.userId,u.username FROM FavouriteEntity f JOIN UserEntity u ON f.learner_id = u.userId WHERE u.userId = :userId", nativeQuery = true)
		List<FavouriteDto> findFavouritesByUserId(@Param("userId") Long userId);*/
}
