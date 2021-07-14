package com.possible.product.order.service.services;

import com.possible.product.order.service.domain.ProductOrder;
import com.possible.product.order.service.domain.Customer;
import com.possible.product.order.service.domain.OrderStatusEnum;
import com.possible.product.order.service.repositories.ProductOrderRepository;
import com.possible.product.order.service.repositories.CustomerRepository;
import com.possible.product.order.service.web.mappers.ProductOrderMapper;
import com.possible.product.order.service.web.model.ProductOrderDto;
import com.possible.product.order.service.web.model.ProductOrderPagedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    private final ProductOrderRepository productOrderRepository;
    private final CustomerRepository customerRepository;
    private final ProductOrderMapper productOrderMapper;
    private final ApplicationEventPublisher publisher;

    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository,
                                   CustomerRepository customerRepository,
                                   ProductOrderMapper productOrderMapper, ApplicationEventPublisher publisher) {
        this.productOrderRepository = productOrderRepository;
        this.customerRepository = customerRepository;
        this.productOrderMapper = productOrderMapper;
        this.publisher = publisher;
    }

    @Override
    public ProductOrderPagedList listOrders(UUID customerId, Pageable pageable) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            Page<ProductOrder> beerOrderPage =
                    productOrderRepository.findAllByCustomer(customerOptional.get(), pageable);

            return new ProductOrderPagedList(beerOrderPage
                    .stream()
                    .map(productOrderMapper::productOrderToDto)
                    .collect(Collectors.toList()), PageRequest.of(
                    beerOrderPage.getPageable().getPageNumber(),
                    beerOrderPage.getPageable().getPageSize()),
                    beerOrderPage.getTotalElements());
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public ProductOrderDto placeOrder(UUID customerId, ProductOrderDto productOrderDto) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            ProductOrder productOrder = productOrderMapper.dtoToProductOrder(productOrderDto);
            productOrder.setId(null); //should not be set by outside client
            productOrder.setCustomer(customerOptional.get());
            productOrder.setOrderStatus(OrderStatusEnum.NEW);

            productOrder.getProductOrderLines().forEach(line -> line.setProductOrder(productOrder));

            ProductOrder savedProductOrder = productOrderRepository.saveAndFlush(productOrder);

            log.debug("Saved Beer Order: " + productOrder.getId());

            //todo impl
          //  publisher.publishEvent(new NewBeerOrderEvent(savedBeerOrder));

            return productOrderMapper.productOrderToDto(savedProductOrder);
        }
        //todo add exception type
        throw new RuntimeException("Customer Not Found");
    }

    @Override
    public ProductOrderDto getOrderById(UUID customerId, UUID orderId) {
        return productOrderMapper.productOrderToDto(getOrder(customerId, orderId));
    }

    @Override
    public void pickupOrder(UUID customerId, UUID orderId) {
        ProductOrder productOrder = getOrder(customerId, orderId);
        productOrder.setOrderStatus(OrderStatusEnum.PICKED_UP);

        productOrderRepository.save(productOrder);
    }

    private ProductOrder getOrder(UUID customerId, UUID orderId){
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if(customerOptional.isPresent()){
            Optional<ProductOrder> beerOrderOptional = productOrderRepository.findById(orderId);

            if(beerOrderOptional.isPresent()){
                ProductOrder productOrder = beerOrderOptional.get();

                // fall to exception if customer id's do not match - order not for customer
                if(productOrder.getCustomer().getId().equals(customerId)){
                    return productOrder;
                }
            }
            throw new RuntimeException("Beer Order Not Found");
        }
        throw new RuntimeException("Customer Not Found");
    }
}
