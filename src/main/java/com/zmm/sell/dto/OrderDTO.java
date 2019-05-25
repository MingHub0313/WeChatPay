package com.zmm.sell.dto;

import com.zmm.sell.domain.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Name OrderDTO
 * @Author 900045
 * @Created by 2019/5/25 0025
 */
@Data
public class OrderDTO {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 买家名称
     */
    private String buyerName;
    /**
     * 买家手机号
     */
    private String buyerPhone;
    /**
     * 买家地址
     */
    private String buyerAddress;
    /**
     * 买家微信 OpenId
     */
    private String buyerOpenid;
    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态 默认为0 新订单
     */
    private Integer orderStatus;

    /**
     *支付状态 默认为 0 未支付
     */
    private Integer payStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private  Date updateTime;

    private List<OrderDetail> orderDetailList;
}
