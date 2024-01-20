package com.bigbasket.app.controller;

import com.bigbasket.app.dto.ProductDto;
import com.bigbasket.app.model.Product;
import com.bigbasket.app.model.User;
import com.bigbasket.app.model.WishList;
import com.bigbasket.app.model.common.ApiResponse;
import com.bigbasket.app.service.AuthTokenService;
import com.bigbasket.app.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    final WishListService wishListService;

    final AuthTokenService authenticationService;

    public WishListController(WishListService wishListService, AuthTokenService authenticationService) {
        this.wishListService = wishListService;
        this.authenticationService = authenticationService;
    }


    // save product as wishlist item
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToWishList(@RequestBody Product product,
                                                     @RequestParam("token") String token) {
        // authenticate the token
        authenticationService.authenticate(token);


        // find the user

        User user = authenticationService.getUser(token);

        // save the item in wishlist

        WishList wishList = new WishList(user, product);

        wishListService.createWishlist(wishList);

        ApiResponse apiResponse = new ApiResponse(true, "Added to wishlist");
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);

    }


    // get all wishlist item for a user

    @GetMapping("/{token}")
    public ResponseEntity<List<ProductDto>> getWishList(@PathVariable("token") String token) {

        // authenticate the token
        authenticationService.authenticate(token);


        // find the user

        User user = authenticationService.getUser(token);

        List<ProductDto> productDtos = wishListService.getWishListForUser(user);

        return new ResponseEntity<>(productDtos, HttpStatus.OK);

    }


}
