package com.barclays.bookstore.cart;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.barclays.bookstore.book.Book;
import org.junit.jupiter.api.Test;

class CartTest {

    @Test
    void addCartItem_expectNewCartWithTotal_whenNoExistingItems() {
        String email = "john@gmail.com";
        Cart cart = Cart.builder().email(email).build();
        Book book = Book.builder().price(220).title("HP").build();
        CartItem cartItem = CartItem.builder().book(book).quantity(2L).build();

        cart.addCartItem(cartItem);

        Cart expectedCart = Cart.builder().email(email).cartItem(cartItem).total(440).build();
        assertThat(cart).isEqualTo(expectedCart);
    }

    @Test
    void addCartItem_expectNewCartWithTotal_whenExistingItems() {
        String email = "john@gmail.com";
        Book book = Book.builder().price(220).title("HP").build();
        CartItem cartItem = CartItem.builder().book(book).quantity(2L).build();
        Cart cart = Cart.builder().cartItem(cartItem).email(email).build();

        cart.addCartItem(cartItem);

        CartItem updateCartItem = CartItem.builder().book(book).quantity(4L).build();
        Cart expectedCart = Cart.builder().email(email).cartItem(updateCartItem).total(880).build();
        assertThat(cart).isEqualTo(expectedCart);
    }
}