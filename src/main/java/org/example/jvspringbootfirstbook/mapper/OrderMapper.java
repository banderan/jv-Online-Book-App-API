package org.example.jvspringbootfirstbook.mapper;

import org.example.jvspringbootfirstbook.config.MapperConfig;
import org.example.jvspringbootfirstbook.dto.order.OrderDto;
import org.example.jvspringbootfirstbook.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {OrderItemMapper.class, BooksMapper.class})
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    OrderDto toDto(Order order);
}
