package com.betheagent.betheagent.properties.controller;

import com.betheagent.betheagent.properties.dto.request.WishListRequestDto;
import com.betheagent.betheagent.properties.dto.response.WishListResponseDto;
import com.betheagent.betheagent.properties.service.FavouriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/btheagent/properties/wishlist")
public class FavouriteController {

    private final FavouriteService favouriteService;

    @PostMapping("/add-item")
    public ResponseEntity<WishListResponseDto> addPropertyToWishList(@PathVariable("userId") String userId, @RequestBody WishListRequestDto wishListRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(favouriteService.addPropertyToWishList(userId, wishListRequestDto));
    }

    @PutMapping("/remove-item/{userId}/{listId}")
    public ResponseEntity<WishListResponseDto> removePropertyFromWishList(@PathVariable("userId") String userId, @PathVariable("listId")String wishListId){
        return ResponseEntity.status(HttpStatus.OK).body(favouriteService.removePropertyFromList(userId, wishListId));
    }
}
