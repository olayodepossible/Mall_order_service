package com.possible.product.order.service.services;

import com.possible.product.order.service.bootstrap.ProductOrderBootStrap;
import com.possible.product.order.service.domain.Customer;
import com.possible.product.order.service.repositories.ProductOrderRepository;
import com.possible.product.order.service.repositories.CustomerRepository;
import com.possible.product.order.service.web.model.ProductOrderDto;
import com.possible.product.order.service.web.model.ProductOrderLineDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class TastingRoomService {

    private final CustomerRepository customerRepository;
    private final ProductOrderService productOrderService;
    private final ProductOrderRepository productOrderRepository;
    private final List<String> beerUpcs = new ArrayList<>(3);

    public TastingRoomService(CustomerRepository customerRepository, ProductOrderService productOrderService,
                              ProductOrderRepository productOrderRepository) {
        this.customerRepository = customerRepository;
        this.productOrderService = productOrderService;
        this.productOrderRepository = productOrderRepository;

        beerUpcs.add(ProductOrderBootStrap.BEER_1_UPC);
        beerUpcs.add(ProductOrderBootStrap.BEER_2_UPC);
        beerUpcs.add(ProductOrderBootStrap.BEER_3_UPC);
    }

    @Transactional
    @Scheduled(fixedRate = 2000) //run every 2 seconds
    public void placeTastingRoomOrder(){

        List<Customer> customerList = customerRepository.findAllByCustomerNameLike(ProductOrderBootStrap.TASTING_ROOM);

        if (customerList.size() == 1){ //should be just one
            doPlaceOrder(customerList.get(0));
        } else {
            log.error("Too many or too few tasting room customers found");
        }
    }

    private void doPlaceOrder(Customer customer) {
        String beerToOrder = getRandomBeerUpc();

        ProductOrderLineDto beerOrderLine = ProductOrderLineDto.builder()
                .upc(beerToOrder)
                .orderQuantity(new Random().nextInt(6)) //todo externalize value to property
                .build();

        List<ProductOrderLineDto> beerOrderLineSet = new ArrayList<>();
        beerOrderLineSet.add(beerOrderLine);

        ProductOrderDto beerOrder = ProductOrderDto.builder()
                .customerId(customer.getId())
                .customerRef(UUID.randomUUID().toString())
                .productOrderLines(beerOrderLineSet)
                .build();

        ProductOrderDto savedOrder = productOrderService.placeOrder(customer.getId(), beerOrder);

    }

    private String getRandomBeerUpc() {
        return beerUpcs.get(new Random().nextInt(beerUpcs.size() -0));
    }
}
