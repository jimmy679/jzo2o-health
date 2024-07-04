package com.jzo2o.health.constant;

/**
 * 支付相关常量
 *
 * @author itcast
 * @create 2023/11/7 17:46
 **/
public class RedisConstants {
    /**
     * 用户端订单滚动分页查询
     */
    public static final String ORDER_PAGE_QUERY = "ORDERS:PAGE_QUERY:PAGE_%s";
    /**
     * 订单ID前缀
     */
    public static final String ORDERS_SHARD_KEY_ID_GENERATOR = "ORDERS:SHARD_KEY:GENERATOR";

}
