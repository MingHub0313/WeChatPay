package com.zmm.sell.controller;

import com.zmm.sell.converter.OrderForm2OrderDTOConverter;
import com.zmm.sell.dto.OrderDTO;
import com.zmm.sell.enums.ResultEnum;
import com.zmm.sell.exception.SellException;
import com.zmm.sell.form.OrderForm;
import com.zmm.sell.service.BuyerService;
import com.zmm.sell.service.OrderMasterService;
import com.zmm.sell.utils.ResultVoUtil;
import com.zmm.sell.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name BuyerOrderController
 * @Author 900045
 * @Created by 2019/5/25 0025
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private BuyerService buyerService;

    /**
     * 创建订单
     * @param orderForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public ResultVo<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO createResult = orderMasterService.create(orderDTO);

        Map<String, String> map = new HashMap<>(1);
        map.put("orderId", createResult.getOrderId());

        return ResultVoUtil.success(map);

    }

    /**
     * 订单列表
     * @param openid
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResultVo<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest request = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderMasterService.findList(openid, request);

        return ResultVoUtil.success(orderDTOPage.getContent());
    }

    /**
     * 订单详情
     * @param openid
     * @param orderId
     * @return
     */
    @GetMapping("/detail")
    public ResultVo<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        // TODO 不安全的做法 findOne(orderId);

        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVoUtil.success(orderDTO);
    }

    /**
     * 取消订单
     * @param openid
     * @param orderId
     * @return
     */
    @PostMapping("/cancel")
    public ResultVo cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
        // TODO 不安全的做法 orderMasterService.cancel(orderDTO)
        buyerService.cancelOrder(openid, orderId);
        return ResultVoUtil.success();
    }

}
