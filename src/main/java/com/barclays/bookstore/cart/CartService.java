package com.barclays.bookstore.cart;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Cart addToCart(String email, CartItem cartItem) {
        Cart cart = cartRepository.findByEmail(email)
            .orElse(Cart.builder().email(email).build());
        cart.addCartItem(cartItem);
        return cartRepository.save(cart);
    }

    public void clear(String email) {
        cartRepository.deleteByEmail(email);
    }

    public Optional<Cart> get(String email) {
        return cartRepository.findByEmail(email);
    }
}
