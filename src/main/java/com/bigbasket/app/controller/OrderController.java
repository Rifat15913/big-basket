package com.bigbasket.app.controller;


import com.bigbasket.app.dto.checkout.CheckoutItemDto;
import com.bigbasket.app.dto.checkout.StripeResponse;
import com.bigbasket.app.service.AuthTokenService;
import com.bigbasket.app.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final AuthTokenService authenticationService;

    private final OrderService orderService;

    public OrderController(AuthTokenService authenticationService, OrderService orderService) {
        this.authenticationService = authenticationService;
        this.orderService = orderService;
    }

    // stripe session checkout api

    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList)
            throws StripeException {
        Session session = orderService.createSession(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        return new ResponseEntity<>(stripeResponse, HttpStatus.OK);

    }




}
