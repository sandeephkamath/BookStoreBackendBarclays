package com.barclays.bookstore.cart;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(("/cart"))
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping()
    public Optional<Cart> getCart(String email) {
        return cartService.get(email);
    }

    @PostMapping()
    public Cart add(@RequestParam String email, @RequestBody CartItem cartItem) {
        return cartService.addToCart(email, cartItem);
    }

    @DeleteMapping()
    public ResponseEntity<Void> clearCart(@RequestParam String email) {
        cartService.clear(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
