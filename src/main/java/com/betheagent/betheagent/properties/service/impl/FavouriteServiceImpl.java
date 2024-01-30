package com.betheagent.betheagent.properties.service.impl;

import com.betheagent.betheagent.authorization.entity.UserInstance;
import com.betheagent.betheagent.authorization.repository.UserRepository;
import com.betheagent.betheagent.exception.customExceptions.BadRequestException;
import com.betheagent.betheagent.exception.customExceptions.PropertyNotFoundException;
import com.betheagent.betheagent.exception.customExceptions.ResourceNotFoundException;
import com.betheagent.betheagent.exception.customExceptions.UserResourceNotFoundException;
import com.betheagent.betheagent.properties.dto.request.WishListRequestDto;
import com.betheagent.betheagent.properties.dto.response.WishListResponseDto;
import com.betheagent.betheagent.properties.entity.Favourites;
import com.betheagent.betheagent.properties.entity.PropertyEntity;
import com.betheagent.betheagent.properties.repository.FavouriteRepository;
import com.betheagent.betheagent.properties.repository.PropertyRepository;
import com.betheagent.betheagent.properties.service.FavouriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavouriteServiceImpl implements FavouriteService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final FavouriteRepository favouriteRepository;

    @Override
    public WishListResponseDto addPropertyToWishList(String userId, WishListRequestDto wishListRequestDto) {
        UserInstance user = userRepository.findById(userId).orElseThrow(() -> new UserResourceNotFoundException("User with id not found. Adding property to wishlist operation failed"));

        PropertyEntity property = propertyRepository.findById(wishListRequestDto.getPropertyId()).orElseThrow(() -> new PropertyNotFoundException("Property with id not found. Adding property to wishlist operation failed"));

        Favourites favourites = Favourites.builder()
                .comment(wishListRequestDto.getComment())
                .property(property)
                .user(user)
                .build();
        user.setFavouriteProperties(List.of(favourites));

        return null;
    }

    @Override
    public WishListResponseDto removePropertyFromList(String userId, String wishListId) {
        UserInstance user = userRepository.findById(userId).orElseThrow(() -> new UserResourceNotFoundException("User not found"));
        if (user.getFavouriteProperties().isEmpty()) {
            throw new BadRequestException("User has no wishList");
        }

        Favourites favourite = favouriteRepository.findById(wishListId).orElseThrow(() -> new ResourceNotFoundException("No favorite item with such id found. Remove property operation failed"));
        log.info(String.valueOf(user.getFavouriteProperties().size()));
        user.getFavouriteProperties().remove(favourite);
        log.info(String.format("After removal, the new size of the favourite property is %d", user.getFavouriteProperties().size()));

        return null;
    }
}
