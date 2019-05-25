package com.zmm.sell.converter;

import com.zmm.sell.domain.OrderMaster;
import com.zmm.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Name OrderMaster2OrderDTOConverter
 * @Author 900045
 * @Created by 2019/5/25 0025
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasters){
        return orderMasters.stream().map(e->
                convert(e)).collect(Collectors.toList());
    }
}
