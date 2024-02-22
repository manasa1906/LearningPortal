package com.example.learningportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.learningportal.dto.FavouriteDto;
import com.example.learningportal.service.FavouriteService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/favourites")
public class FavouriteController {
	@Autowired
	private FavouriteService favouriteService;

	@GetMapping("/findAll")
	public List<FavouriteDto> findAllFavourites() {
		log.info("feching all the favourites");
		return favouriteService.getAllFavourites();
	}

	@GetMapping("/{favouriteId}")
	public FavouriteDto findById(@PathVariable("favouriteId") Long id) {
		log.info("fetcing the favourite by Id");
		return favouriteService.getFavouriteById(id);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteFavourite(@PathVariable("id") Long id) {
		log.info("favourite is deleted");
		favouriteService.deleteFavourite(id);
	}

	@PostMapping("/createFavourite")
	public FavouriteDto saveFavourite(@RequestBody FavouriteDto request) {
		log.info("favourite is created");
		return favouriteService.saveFavourite(request);
	}

	/*	@GetMapping("/findfavourites/{userId}")
		public List<FavouriteDto> getFavouritesByUserId(@PathVariable("userId") Long userId) {
			log.info("fetching favourites using userId");
			return favouriteService.getFavouritesByUserId(userId);
		}*/

}