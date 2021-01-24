package com.barclays.bookstore.cart;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByEmail(String email);

    void deleteByEmail(String email);

}
