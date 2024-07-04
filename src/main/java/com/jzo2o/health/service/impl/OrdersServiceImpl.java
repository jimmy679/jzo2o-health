package com.jzo2o.health.service.impl;


import cn.hutool.db.sql.Order;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.common.utils.BeanUtils;
import com.jzo2o.common.utils.DateUtils;
import com.jzo2o.health.enums.OrderPayStatusEnum;
import com.jzo2o.health.enums.OrderStatusEnum;
import com.jzo2o.health.mapper.OrdersMapper;
import com.jzo2o.health.model.domain.Member;
import com.jzo2o.health.model.domain.Orders;
import com.jzo2o.health.model.domain.Setmeal;
import com.jzo2o.health.model.dto.request.PlaceOrderReqDTO;
import com.jzo2o.health.model.dto.response.PlaceOrderResDTO;
import com.jzo2o.health.service.IOrdersService;
import com.jzo2o.mvc.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.metadata.OracleTableMetaDataProvider;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.jzo2o.health.constant.RedisConstants.ORDERS_SHARD_KEY_ID_GENERATOR;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-07-04
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {


    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private SetmealServiceImpl setmealService;
    @Resource
    private MemberServiceImpl memberService;

    /**
     * 生成订单id 格式：{yyMMdd}{13位id}
     *
     * @return
     */
    private Long generateOrderId() {
        //通过Redis自增序列得到序号
        Long id = redisTemplate.opsForValue().increment(ORDERS_SHARD_KEY_ID_GENERATOR, 1);
        //生成订单号   2位年+2位月+2位日+13位序号
        long orderId = DateUtils.getFormatDate(LocalDateTime.now(), "yyMMdd") * 10000000000000L + id;
        return orderId;
    }

    @Override
    public PlaceOrderResDTO place(PlaceOrderReqDTO placeOrderReqDTO) {
        //基本信息
        Orders orders = new Orders();
        Long orderId = generateOrderId();
        orders.setId(orderId);
        orders.setOrderStatus(OrderStatusEnum.NO_PAY.getStatus());
        orders.setPayStatus(OrderPayStatusEnum.NO_PAY.getStatus());
        //套餐信息
        Integer setmealId = placeOrderReqDTO.getSetmealId();
        orders.setSetmealId(setmealId);
        Setmeal setmeal = setmealService.findById(setmealId);
        orders.setSetmealName(setmeal.getName());
        orders.setSetmealSex(setmeal.getSex());
        orders.setSetmealAge(setmeal.getAge());
        orders.setSetmealRemark(setmeal.getRemark());
        orders.setSetmealPrice(BigDecimal.valueOf(setmeal.getPrice()));
        orders.setSetmealImg(setmeal.getImg());
        //体检人信息
        orders.setReservationDate(placeOrderReqDTO.getReservationDate());
        orders.setCheckupPersonName(placeOrderReqDTO.getCheckupPersonName());
        orders.setCheckupPersonSex(placeOrderReqDTO.getCheckupPersonSex());
        orders.setCheckupPersonPhone(placeOrderReqDTO.getCheckupPersonPhone());
        orders.setCheckupPersonIdcard(placeOrderReqDTO.getCheckupPersonIdcard());
        //获取会员信息
        Long id = null;
        //todo 尝试获取用户信息但爆空指针，先写死
        id = 1719998606804688897L;
        Member member = memberService.getById(id);
        orders.setMemberPhone(member.getPhone());
        orders.setMemberId(id);
        //插入、封装返回实体
        this.save(orders);
        PlaceOrderResDTO placeOrderResDTO = new PlaceOrderResDTO();
        placeOrderResDTO.setId(orderId);
        return placeOrderResDTO;
    }
}
