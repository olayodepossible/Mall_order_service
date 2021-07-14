package com.possible.product.order.service.web.mappers;

import com.possible.product.order.service.domain.ProductOrderLine;
import com.possible.product.order.service.domain.ProductOrderLine.ProductOrderLineBuilder;
import com.possible.product.order.service.web.model.ProductOrderLineDto;
import com.possible.product.order.service.web.model.ProductOrderLineDto.ProductOrderLineDtoBuilder;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-15T00:51:21+0100",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class ProductOrderLineMapperImpl implements ProductOrderLineMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public ProductOrderLineDto productOrderLineToDto(ProductOrderLine line) {
        if ( line == null ) {
            return null;
        }

        ProductOrderLineDtoBuilder productOrderLineDto = ProductOrderLineDto.builder();

        productOrderLineDto.id( line.getId() );
        if ( line.getVersion() != null ) {
            productOrderLineDto.version( line.getVersion().intValue() );
        }
        productOrderLineDto.createdDate( dateMapper.asOffsetDateTime( line.getCreatedDate() ) );
        productOrderLineDto.lastModifiedDate( dateMapper.asOffsetDateTime( line.getLastModifiedDate() ) );
        productOrderLineDto.orderQuantity( line.getOrderQuantity() );

        return productOrderLineDto.build();
    }

    @Override
    public ProductOrderLine dtoToProductOrderLine(ProductOrderLineDto dto) {
        if ( dto == null ) {
            return null;
        }

        ProductOrderLineBuilder productOrderLine = ProductOrderLine.builder();

        productOrderLine.id( dto.getId() );
        if ( dto.getVersion() != null ) {
            productOrderLine.version( dto.getVersion().longValue() );
        }
        productOrderLine.createdDate( dateMapper.asTimestamp( dto.getCreatedDate() ) );
        productOrderLine.lastModifiedDate( dateMapper.asTimestamp( dto.getLastModifiedDate() ) );
        productOrderLine.orderQuantity( dto.getOrderQuantity() );

        return productOrderLine.build();
    }
}
