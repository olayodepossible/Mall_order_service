package com.possible.product.order.service.web.mappers;

import com.possible.product.order.service.domain.ProductOrder;
import com.possible.product.order.service.web.model.ProductOrderDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class, ProductOrderLineMapper.class})
public interface ProductOrderMapper {

    ProductOrderDto productOrderToDto(ProductOrder productOrder);

    ProductOrder dtoToProductOrder(ProductOrderDto dto);
}
