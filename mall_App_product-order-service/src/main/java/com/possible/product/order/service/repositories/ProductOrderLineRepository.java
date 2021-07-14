package com.possible.product.order.service.repositories;

import com.possible.product.order.service.domain.ProductOrderLine;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ProductOrderLineRepository extends PagingAndSortingRepository<ProductOrderLine, UUID> {
}
