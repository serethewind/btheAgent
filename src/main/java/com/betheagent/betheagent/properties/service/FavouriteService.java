package com.betheagent.betheagent.properties.service;

import com.betheagent.betheagent.properties.dto.request.WishListRequestDto;
import com.betheagent.betheagent.properties.dto.response.WishListResponseDto;

public interface FavouriteService {
    WishListResponseDto addPropertyToWishList(String userId, WishListRequestDto wishListRequestDto);

    WishListResponseDto removePropertyFromList(String userId, String wishListId);

    WishListResponseDto removeAllFavourites(String userId);
}
