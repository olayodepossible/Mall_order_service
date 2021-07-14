package com.possible.product.order.service.web.mappers;

import com.possible.product.order.service.domain.ProductOrder;
import com.possible.product.order.service.domain.ProductOrder.ProductOrderBuilder;
import com.possible.product.order.service.domain.ProductOrderLine;
import com.possible.product.order.service.web.model.OrderStatusEnum;
import com.possible.product.order.service.web.model.ProductOrderDto;
import com.possible.product.order.service.web.model.ProductOrderDto.ProductOrderDtoBuilder;
import com.possible.product.order.service.web.model.ProductOrderLineDto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-15T00:51:21+0100",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
@Component
public class ProductOrderMapperImpl implements ProductOrderMapper {

    @Autowired
    private DateMapper dateMapper;
    @Autowired
    private ProductOrderLineMapper productOrderLineMapper;

    @Override
    public ProductOrderDto productOrderToDto(ProductOrder productOrder) {
        if ( productOrder == null ) {
            return null;
        }

        ProductOrderDtoBuilder productOrderDto = ProductOrderDto.builder();

        productOrderDto.id( productOrder.getId() );
        if ( productOrder.getVersion() != null ) {
            productOrderDto.version( productOrder.getVersion().intValue() );
        }
        productOrderDto.createdDate( dateMapper.asOffsetDateTime( productOrder.getCreatedDate() ) );
        productOrderDto.lastModifiedDate( dateMapper.asOffsetDateTime( productOrder.getLastModifiedDate() ) );
        productOrderDto.productOrderLines( productOrderLineSetToProductOrderLineDtoList( productOrder.getProductOrderLines() ) );
        productOrderDto.orderStatus( orderStatusEnumToOrderStatusEnum( productOrder.getOrderStatus() ) );
        productOrderDto.orderStatusCallbackUrl( productOrder.getOrderStatusCallbackUrl() );
        productOrderDto.customerRef( productOrder.getCustomerRef() );

        return productOrderDto.build();
    }

    @Override
    public ProductOrder dtoToProductOrder(ProductOrderDto dto) {
        if ( dto == null ) {
            return null;
        }

        ProductOrderBuilder productOrder = ProductOrder.builder();

        productOrder.id( dto.getId() );
        if ( dto.getVersion() != null ) {
            productOrder.version( dto.getVersion().longValue() );
        }
        productOrder.createdDate( dateMapper.asTimestamp( dto.getCreatedDate() ) );
        productOrder.lastModifiedDate( dateMapper.asTimestamp( dto.getLastModifiedDate() ) );
        productOrder.customerRef( dto.getCustomerRef() );
        productOrder.productOrderLines( productOrderLineDtoListToProductOrderLineSet( dto.getProductOrderLines() ) );
        productOrder.orderStatus( orderStatusEnumToOrderStatusEnum1( dto.getOrderStatus() ) );
        productOrder.orderStatusCallbackUrl( dto.getOrderStatusCallbackUrl() );

        return productOrder.build();
    }

    protected List<ProductOrderLineDto> productOrderLineSetToProductOrderLineDtoList(Set<ProductOrderLine> set) {
        if ( set == null ) {
            return null;
        }

        List<ProductOrderLineDto> list = new ArrayList<ProductOrderLineDto>( set.size() );
        for ( ProductOrderLine productOrderLine : set ) {
            list.add( productOrderLineMapper.productOrderLineToDto( productOrderLine ) );
        }

        return list;
    }

    protected OrderStatusEnum orderStatusEnumToOrderStatusEnum(com.possible.product.order.service.domain.OrderStatusEnum orderStatusEnum) {
        if ( orderStatusEnum == null ) {
            return null;
        }

        OrderStatusEnum orderStatusEnum1;

        switch ( orderStatusEnum ) {
            case NEW: orderStatusEnum1 = OrderStatusEnum.NEW;
            break;
            case READY: orderStatusEnum1 = OrderStatusEnum.READY;
            break;
            case PICKED_UP: orderStatusEnum1 = OrderStatusEnum.PICKED_UP;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + orderStatusEnum );
        }

        return orderStatusEnum1;
    }

    protected Set<ProductOrderLine> productOrderLineDtoListToProductOrderLineSet(List<ProductOrderLineDto> list) {
        if ( list == null ) {
            return null;
        }

        Set<ProductOrderLine> set = new HashSet<ProductOrderLine>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( ProductOrderLineDto productOrderLineDto : list ) {
            set.add( productOrderLineMapper.dtoToProductOrderLine( productOrderLineDto ) );
        }

        return set;
    }

    protected com.possible.product.order.service.domain.OrderStatusEnum orderStatusEnumToOrderStatusEnum1(OrderStatusEnum orderStatusEnum) {
        if ( orderStatusEnum == null ) {
            return null;
        }

        com.possible.product.order.service.domain.OrderStatusEnum orderStatusEnum1;

        switch ( orderStatusEnum ) {
            case NEW: orderStatusEnum1 = com.possible.product.order.service.domain.OrderStatusEnum.NEW;
            break;
            case READY: orderStatusEnum1 = com.possible.product.order.service.domain.OrderStatusEnum.READY;
            break;
            case PICKED_UP: orderStatusEnum1 = com.possible.product.order.service.domain.OrderStatusEnum.PICKED_UP;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + orderStatusEnum );
        }

        return orderStatusEnum1;
    }
}
