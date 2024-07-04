package com.jzo2o.health.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.health.model.domain.Orders;
import com.jzo2o.health.model.dto.request.PlaceOrderReqDTO;
import com.jzo2o.health.model.dto.response.PlaceOrderResDTO;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author author
 * @since 2024-07-04
 */
public interface IOrdersService extends IService<Orders> {

    PlaceOrderResDTO place(PlaceOrderReqDTO placeOrderReqDTO);
}
