package org.example.jvspringbootfirstbook.mapper;

import org.example.jvspringbootfirstbook.config.MapperConfig;
import org.example.jvspringbootfirstbook.dto.order.OrderItemDto;
import org.example.jvspringbootfirstbook.model.CartItem;
import org.example.jvspringbootfirstbook.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {CartItemMapper.class, BooksMapper.class})
public interface OrderItemMapper {
    OrderItem fromCarttoOrderItem(CartItem cartItem);

    @Mapping(target = "bookId", source = "book.id")
    OrderItemDto toDto(OrderItem orderItem);
}
