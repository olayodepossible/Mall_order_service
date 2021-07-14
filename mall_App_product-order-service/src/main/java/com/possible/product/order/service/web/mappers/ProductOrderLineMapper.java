package com.possible.product.order.service.web.mappers;

import com.possible.product.order.service.domain.ProductOrderLine;
import com.possible.product.order.service.web.model.ProductOrderLineDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface ProductOrderLineMapper {
    ProductOrderLineDto productOrderLineToDto(ProductOrderLine line);

    ProductOrderLine dtoToProductOrderLine(ProductOrderLineDto dto);
}
