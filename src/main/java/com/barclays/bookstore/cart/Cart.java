package com.barclays.bookstore.cart;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    @Singular
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    private double total;

    public void addCartItem(CartItem cartItem) {
        List<CartItem> cartItems = new ArrayList<>(this.cartItems);
        cartItems.add(cartItem);
        List<CartItem> updatedItems = cartItems.stream()
            .collect(groupingBy(CartItem::getBook, summingLong(CartItem::getQuantity)))
            .entrySet().stream()
            .map(
                entrySet -> CartItem.builder().book(entrySet.getKey()).quantity(entrySet.getValue())
                    .build())
            .filter(item -> item.getQuantity() > 0)
            .collect(Collectors.toList());
        setCartItems(updatedItems);
        double total = cartItems.stream().mapToDouble(CartItem::getTotal).sum();
        setTotal(total);
    }
}
