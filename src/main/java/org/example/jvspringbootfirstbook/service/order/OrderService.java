package org.example.jvspringbootfirstbook.service.order;

import java.util.List;
import org.example.jvspringbootfirstbook.dto.order.OrderDto;
import org.example.jvspringbootfirstbook.dto.order.OrderItemDto;
import org.example.jvspringbootfirstbook.dto.order.PlacingOrderRequestDto;
import org.example.jvspringbootfirstbook.model.User;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDto makeOrder(PlacingOrderRequestDto requestDto);

    List<OrderDto> getHistory(User user, Pageable pageable);

    List<OrderItemDto> getFromOrder(Long orderId);

    OrderItemDto getFromOrder(Long orderId, Long id);

    OrderDto updateStatus(Long id);
}
