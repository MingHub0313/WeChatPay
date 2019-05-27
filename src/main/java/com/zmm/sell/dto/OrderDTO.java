package com.zmm.sell.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zmm.sell.domain.OrderDetail;
import com.zmm.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Name OrderDTO
 * @Author 900045
 * @Created by 2019/5/25 0025
 * // 过时@JsonSerialize
 * @JsonInclude(JsonInclude.Include.NON_NULL)
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
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private  Date updateTime;

    /**
     * 通过new ArrayList<>()  当数据为空时 返回前台 []
     * = new ArrayList<>()
     */
    private List<OrderDetail> orderDetailList ;
}
