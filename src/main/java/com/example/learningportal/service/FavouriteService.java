package com.example.learningportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.learningportal.dto.FavouriteDto;
import com.example.learningportal.entities.FavouriteEntity;
import com.example.learningportal.mappers.FavouriteMapper;
import com.example.learningportal.repository.FavouriteRepository;

@Service
public class FavouriteService {
	@Autowired
	private FavouriteRepository favouriteRepository;
	@Autowired
	private FavouriteMapper favouriteMapper;

	public FavouriteDto saveFavourite(FavouriteDto dto) {
		FavouriteEntity entity = favouriteMapper.toEntity(dto);
		favouriteRepository.save(entity);
		entity.setCreatedOn(LocalDateTime.now());
		entity.setUpdatedOn(LocalDateTime.now());
		return favouriteMapper.toDto(entity);

	}

	public FavouriteDto getFavouriteById(Long favouriteId) {
		Optional<FavouriteEntity> FavouriteOptional = favouriteRepository.findById(favouriteId);
		FavouriteEntity Favourite = FavouriteOptional.get();
		return favouriteMapper.toDto(Favourite);
	}

	public List<FavouriteDto> getAllFavourites() {
		List<FavouriteEntity> entitylist = favouriteRepository.findAll();
		return favouriteMapper.toDto(entitylist);
	}

	public void deleteFavourite(Long id) {
		favouriteRepository.deleteById(id);
	}

	/*public List<FavouriteDto> getFavouritesByUserId(Long userId) {
		return favouriteRepository.findFavouritesByUserId(userId);
	}*/

}
