package com.innobyte.services.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobyte.services.models.OrderItems;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {

	Optional<OrderItems> findByProductIdAndOrderIdAndUserId(Long productId,Long orderId,Long userId);
}
