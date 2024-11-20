package com.innobyte.services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobyte.services.models.Wishlist;
@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

}
