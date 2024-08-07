package org.example.jvspringbootfirstbook.service.order;

import lombok.RequiredArgsConstructor;
import org.example.jvspringbootfirstbook.dto.order.OrderDto;
import org.example.jvspringbootfirstbook.dto.order.OrderItemDto;
import org.example.jvspringbootfirstbook.dto.order.PlacingOrderRequestDto;
import org.example.jvspringbootfirstbook.exception.EntityNotFoundException;
import org.example.jvspringbootfirstbook.mapper.OrderItemMapper;
import org.example.jvspringbootfirstbook.mapper.OrderMapper;
import org.example.jvspringbootfirstbook.model.*;
import org.example.jvspringbootfirstbook.repository.cart.ShoppingCartRepository;
import org.example.jvspringbootfirstbook.repository.order.OrderItemRepository;
import org.example.jvspringbootfirstbook.repository.order.OrderRepository;
import org.example.jvspringbootfirstbook.repository.user.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;

    @Override
    public OrderDto makeOrder(PlacingOrderRequestDto requestDto) {
        User user = userRepository.findByShippingAddress(requestDto.shippingAddress())
                .orElseThrow(
                        () -> new EntityNotFoundException("We don't have a shipping address")
                );
        Order emptyOrder = getEmptyOrder(requestDto, user);

        ShoppingCart cart = shoppingCartRepository
                .findShoppingCartByUser(user);
        Set<OrderItem> orderItemSet = getOrderItemSet(cart, emptyOrder);

        Set<OrderItem> orderItemsWithPrice = calculatePriceForSet(orderItemSet);
        emptyOrder.setOrderItems(orderItemsWithPrice);

        BigDecimal total = orderItemsWithPrice.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        emptyOrder.setTotal(total);

        Order filledOrder = orderRepository.save(emptyOrder);
        return orderMapper.toDto(filledOrder);
    }

    private Order getEmptyOrder(PlacingOrderRequestDto requestDto, User user) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Status.PENDING);
        order.setTotal(BigDecimal.ZERO);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.shippingAddress());
        order.setOrderItems(new HashSet<>());
        return orderRepository.save(order);
    }

    private Set<OrderItem> calculatePriceForSet(Set<OrderItem> orderItemSet) {
        return orderItemSet.stream()
                .map(this::setPrice)
                .collect(Collectors.toSet());
    }

    private OrderItem setPrice(OrderItem orderItem) {
        orderItem.setPrice(
                orderItem.getBook().getPrice().multiply(
                        BigDecimal.valueOf(orderItem.getQuantity())
                )
        );
        return orderItem;
    }

    private Set<OrderItem> getOrderItemSet(ShoppingCart cart, Order emptyOrder) {
        Set<CartItem> cartItems = cart.getCartItems();
        return cartItems.stream()
                .map(orderItemMapper::fromCarttoOrderItem)
                .map(orderItem -> setOrderToItem(orderItem, emptyOrder))
                .collect(Collectors.toSet());
    }

    private OrderItem setOrderToItem(OrderItem orderItem, Order emptyOrder) {
        orderItem.setOrder(emptyOrder);
        return orderItem;
    }

    @Override
    public List<OrderDto> getHistory(User user, Pageable pageable) {
        return orderRepository.findAllByUser(user, pageable).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public List<OrderItemDto> getFromOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(
                        () -> new EntityNotFoundException(
                                "We don't have order with this id: " + orderId)
                );
        return order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getFromOrder(Long orderId, Long id) {
        List<OrderItemDto> fromOrder = getFromOrder(orderId);
        String fromLong = Long.toString(id);
        return fromOrder.get(Integer.parseInt(fromLong));
    }

    @Override
    public OrderDto updateStatus(Long id) {
        if (!orderRepository.findById(id).isPresent()) {
            throw new EntityNotFoundException("We don't have a order");
        }
        Order order = orderRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("We don't have a order")
                );
        if (order.getStatus().equals(Status.PENDING)) {
            order.setStatus(Status.DELIVERED);
            return getDto(order);
        } else if (order.getStatus().equals(Status.DELIVERED)) {
            order.setStatus(Status.COMPLETED);
            return getDto(order);
        } else {
            order.setDeleted(true);
            return getDto(order);
        }
    }

    private OrderDto getDto(Order order) {
        return orderMapper.toDto(orderRepository.save(order));
    }
}
