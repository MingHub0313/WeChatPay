package com.zmm.sell.domain;

import com.zmm.sell.enums.OpenStatusEnum;
import com.zmm.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Name OrderMaster
 * @Author 900045
 * @Created by 2019/5/24 0024
 */
@Entity
@DynamicUpdate
@Data
public class OrderMaster {


    /**
     * 订单Id
     */
    @Id
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
    private Integer orderStatus= OpenStatusEnum.NEW.getCode();

    /**
     *支付状态 默认为 0 未支付
     */
    private int payStatus= PayStatusEnum.WAIT.getCode();

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private  Date updateTime;
}
