package com.hh.demo.service.impl;

import com.hh.demo.common.Consts;
import com.hh.demo.common.ProductStatusEnum;
import com.hh.demo.common.ServerResponse;
import com.hh.demo.common.StatusEnum;
import com.hh.demo.dao.OrderItemMapper;
import com.hh.demo.dao.OrderMapper;
import com.hh.demo.entity.VO.OrderItemVO;
import com.hh.demo.entity.VO.OrderVO;
import com.hh.demo.entity.VO.ProductDetailVo;
import com.hh.demo.entity.pojo.Cart;
import com.hh.demo.entity.pojo.Order;
import com.hh.demo.entity.pojo.OrderItem;
import com.hh.demo.exception.BusinessException;
import com.hh.demo.service.ICartService;
import com.hh.demo.service.IOrderService;
import com.hh.demo.service.IProductService;
import com.hh.demo.utils.BigDecimalUtil;
import com.hh.demo.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderService implements IOrderService {

    @Autowired
    ICartService cartService;
    @Autowired
    IProductService productService;
    @Resource
    OrderMapper orderMapper;
    @Resource
    OrderItemMapper orderItemMapper;

    @Transactional(isolation = Isolation.REPEATABLE_READ,timeout = 10,readOnly =false,rollbackFor = {BusinessException.class},
            propagation = Propagation.REQUIRED
    )


    /**
     * 创建订单
     */
    @Override
    public ServerResponse createOrder(Integer userId, Integer shippingId) {

        //1.参数非空判断
        if (shippingId == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.ADDRESS_NOT_EMPTY.getStatus(),
                    StatusEnum.ADDRESS_NOT_EMPTY.getMsg()
            );
        }

        //2.根据userId查询购物车中已经选中的商品List<Cart>


        //需要判断嘛？不是肯定为true嘛？
        ServerResponse<List<Cart>> serverResponse = cartService.findCartByUserIdAndChecked(userId);
        if (!serverResponse.isSuccess()){
            return serverResponse;
        }

        List<Cart> cartList = serverResponse.getData();
        if (cartList == null || cartList.size() == 0){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.USER_CART_EXISTS.getStatus(),
                    StatusEnum.USER_CART_EXISTS.getMsg()
            );
        }

        //3.List<Cart> 转换为 List<OrderItem>
        ServerResponse<List<OrderItem>> serverResponse1 = assembleOrderItemList(cartList);
        if (!serverResponse1.isSuccess()){
            return serverResponse1;
        }

        //4.生成订单，并插入订单库
        List<OrderItem> orderItemList = serverResponse1.getData();
        ServerResponse<Order> serverResponse2 = generateOrder(userId,orderItemList,shippingId);
        if (!serverResponse2.isSuccess()){
            return serverResponse2;
        }

        //5.将订单明细批量插入订单明细库
        Order order = serverResponse2.getData();
        for (OrderItem orderItem : orderItemList){
            orderItem.setOrderNo(order.getOrderNo());
        }

        int count = orderItemMapper.insertBatch(orderItemList);
        if (count <= 0){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.ORDER_ITEM_CREATE_FAIL.getStatus(),
                    StatusEnum.ORDER_ITEM_CREATE_FAIL.getMsg()
            );
        }

        //6.商品减库存
        reduceStock(orderItemList);


        //7.清空购物车中已经下单的商品
        ServerResponse serverResponse3 = cleanCart(cartList);
        if (!serverResponse3.isSuccess()){
            return serverResponse3;
        }

        //返回OrderVO
        OrderVO orderVO = assembleOrderVO(order,orderItemList,shippingId);

        return ServerResponse.serverResponseBySuccess(null,orderVO);
    }

    public OrderVO assembleOrderVO(Order order,List<OrderItem> orderItemList,Integer shippingId){
        OrderVO orderVO = new OrderVO();
        orderVO.setUserId(order.getUserId());
        orderVO.setOrderNo(order.getOrderNo());
        orderVO.setPayment(order.getPayment());
        orderVO.setPaymentType(order.getPaymentType());
        orderVO.setPostage(order.getPostage());
        orderVO.setStatus(order.getStatus());
        orderVO.setPaymentTime(DateUtils.date2Str(order.getPaymentTime()));
        orderVO.setSendTime(DateUtils.date2Str(order.getSendTime()));
        orderVO.setEndTime(DateUtils.date2Str(order.getEndTime()));
        orderVO.setCreateTime(DateUtils.date2Str(order.getCreateTime()));
        orderVO.setEndTime(DateUtils.date2Str(order.getEndTime()));
        orderVO.setCloseTime(DateUtils.date2Str(order.getCloseTime()));

        orderVO.setShippingId(shippingId);

        List<OrderItemVO> orderItemVOList = new ArrayList<>();

        for (OrderItem orderItem : orderItemList){
            OrderItemVO orderItemVO = convertOrderItemVO(orderItem);
            orderItemVOList.add(orderItemVO);
        }

        orderVO.setOrderItemVOList(orderItemVOList);

        return orderVO;
    }

    /**
     * OrderItem -> OrderItemVO
     */
    public OrderItemVO convertOrderItemVO(OrderItem orderItem){

        if (orderItem == null){
            return null;
        }

        OrderItemVO orderItemVO = new OrderItemVO() ;

        orderItemVO.setOrderNo(orderItem.getOrderNo());
        orderItemVO.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVO.setProductId(orderItem.getProductId());
        orderItemVO.setCreateTime(DateUtils.date2Str(orderItem.getCreateTime()));
        orderItemVO.setProductImage(orderItem.getProductImage());
        orderItemVO.setProductName(orderItem.getProductName());
        orderItemVO.setQuantity(orderItem.getQuantity());
        orderItemVO.setTotalPrice(orderItem.getTotalPrice());


        return orderItemVO;
    }


    /**
     * 清空购物车已下单商品
     * delete from cart where id in (1,2,3)
     */
    private  ServerResponse cleanCart(List<Cart> cartList){

        return cartService.deleteBatchByIds(cartList);

    }


    /**
     * 减库存
     * */
    private ServerResponse reduceStock(List<OrderItem> orderItemList) {
        for (OrderItem orderItem : orderItemList){
            Integer productId = orderItem.getProductId();
            Integer quantity = orderItem.getQuantity();
            //根据商品id减库存
            ServerResponse serverResponse = productService.reduceStock(productId,quantity,0);
            if (!serverResponse.isSuccess()){
                return serverResponse;
            }
        }
        return ServerResponse.serverResponseBySuccess(null,null);
    }


    private ServerResponse generateOrder(Integer userId,List<OrderItem> orderItems,Integer shippingId){
        Order order = new Order();

        order.setUserId(userId);
        order.setShippingId(shippingId);

        //设置订单总金额
        order.setPayment(getOrderTotalPrice(orderItems));
        order.setPaymentType(1);
        order.setPostage(0);
        order.setStatus(Consts.OrderStatusEnum.UNPAY.getStatus());
        order.setOrderNo(generateOrderNo());

        //将订单落库
        int result = orderMapper.insert(order);
        if (result <= 0){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.ORDER_CREATE_FAIL.getStatus(),
                    StatusEnum.ORDER_CREATE_FAIL.getMsg()
            );
        }
        return ServerResponse.serverResponseBySuccess(null,order);
    }

    private Long generateOrderNo(){
        return System.currentTimeMillis();
    }

    /**
     * 计算总金额
     * */
    private BigDecimal getOrderTotalPrice(List<OrderItem> orderItems){
        BigDecimal orderTotalPrice = new BigDecimal("0");

        for (OrderItem orderItem : orderItems){
            orderTotalPrice = BigDecimalUtil.add(
                    String.valueOf(orderTotalPrice.doubleValue()),
                    String.valueOf(orderItem.getCurrentUnitPrice())
            );
        }
        return orderTotalPrice;
    }

    private ServerResponse assembleOrderItemList(List<Cart> cartList){

        List<OrderItem> orderItemList = new ArrayList<>();

        for (Cart c : cartList){
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(c.getProductId());
            orderItem.setQuantity(c.getQuantity());
            orderItem.setUserId(c.getUserId());

            //根据商品id查询商品信息
            ServerResponse<ProductDetailVo> serverResponse = productService.detail(c.getProductId());
            if (!serverResponse.isSuccess()){
                return serverResponse;
            }

            //商品是否处于在售
            ProductDetailVo productDetailVo = serverResponse.getData();
            if (productDetailVo.getStatus() != ProductStatusEnum.PRODUCT_ONLINE.getStatus()){
                return ServerResponse.serverResponseByFail(
                        StatusEnum.PRODUCT_OFFLINE_OR_DELETE.getStatus(),
                        StatusEnum.PRODUCT_OFFLINE_OR_DELETE.getMsg()
                );
            }
            //判断商品库存是否充足
            if (productDetailVo.getStock() < c.getQuantity()){
                return ServerResponse.serverResponseByFail(
                        StatusEnum.PRODUCT_STOCK_NOT_FULL.getStatus(),
                        StatusEnum.PRODUCT_STOCK_NOT_FULL.getMsg()
                );
            }
            orderItem.setCurrentUnitPrice(productDetailVo.getPrice());
            orderItem.setProductImage(productDetailVo.getMainImage());
            orderItem.setProductName(productDetailVo.getName());
            orderItem.setTotalPrice(
                    BigDecimalUtil.multi(
                            String.valueOf(productDetailVo.getPrice().doubleValue()),
                            String.valueOf(c.getQuantity())
                    ))
            ;
            orderItemList.add(orderItem);

        }


        return ServerResponse.serverResponseBySuccess(null,orderItemList);

    }


    //定时任务
    //每隔10秒执行一次
    //@Scheduled(cron = "0/10 * * * * ?")
    public void closeOrder(){


        System.out.println("========测试定时任务========");

    }


    /**
     * 取消订单
     * */
    @Override
    public ServerResponse cancelOrder(Long orderNo) {

        //1.参数校验
        if(orderNo==null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PARAM_NOT_EMPTY.getStatus(),
                    StatusEnum.PARAM_NOT_EMPTY.getMsg()
            );
        }

        //2.根据订单号查询订单是否存在

        Order order= orderMapper.findOrderByOrderNo(orderNo);

        if(order==null){//订单不存在
            return ServerResponse.serverResponseByFail(
                    StatusEnum.ORDER_NOT_EXISTS.getStatus(),
                    StatusEnum.ORDER_NOT_EXISTS.getMsg()
            );
        }
        //只有未付款的订单才能取消
        if(order.getStatus()!=Consts.OrderStatusEnum.UNPAY.getStatus()){

            return ServerResponse.serverResponseByFail(StatusEnum.ORDER_NOT_CANCEL.getStatus(),StatusEnum.ORDER_NOT_CANCEL.getMsg());
        }

        //取消订单
        order.setStatus(Consts.OrderStatusEnum.CANCELED.getStatus());
        int count=orderMapper.updateByPrimaryKey(order);

        if(count<=0){
            //订单取消失败
            return ServerResponse.serverResponseByFail(StatusEnum.ORDER_CANCEL_FAIL.getStatus(),StatusEnum.ORDER_CANCEL_FAIL.getMsg());
        }


        //更新库存
        List<OrderItem> orderItemLst=orderItemMapper.findOrderItemsByOrderNo(orderNo);

        for(OrderItem orderItem:orderItemLst){
            Integer quantity= orderItem.getQuantity();
            Integer productId=orderItem.getProductId();

            ServerResponse response=productService.reduceStock(productId,quantity,1);

            if(!response.isSuccess()){
                return ServerResponse.serverResponseByFail(StatusEnum.REDUCE_STOCK_FAIL.getStatus(),StatusEnum.REDUCE_STOCK_FAIL.getMsg());
            }
        }


        return ServerResponse.serverResponseBySuccess(null,null);

    }


    @Override
    public ServerResponse findOrderByOrderNo(Long orderNo) {

        //step1:参数非空校验
        if (orderNo == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PARAM_NOT_EMPTY.getStatus(),
                    StatusEnum.PARAM_NOT_EMPTY.getMsg()
            );
        }

        //step2:根据订单号查询订单
        Order order=orderMapper.findOrderByOrderNo(orderNo);
        if(order==null){
            return ServerResponse.serverResponseByFail(StatusEnum.ORDER_NOT_EXISTS.getStatus(),StatusEnum.ORDER_NOT_EXISTS.getMsg());
        }
        List<OrderItem> orderItemList=orderItemMapper.findOrderItemsByOrderNo(orderNo);


        OrderVO orderVO=assembleOrderVO(order,orderItemList,order.getShippingId());


        return ServerResponse.serverResponseBySuccess(null,orderVO);
    }


    @Override
    public ServerResponse updateOrderStatus(
            Long orderNo, String payTime, Integer orderStatus) {

        if (orderNo == null || payTime == null || "".equals(payTime) ||orderStatus == null){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.PARAM_NOT_EMPTY.getStatus(),
                    StatusEnum.PARAM_NOT_EMPTY.getMsg()
            );
        }

        int result = orderMapper.updateOrderStatus(orderNo,DateUtils.srt2Date(payTime),orderStatus);
        if (result <= 0){
            return ServerResponse.serverResponseByFail(
                    StatusEnum.ORDER_STATUS_FAIL.getStatus(),
                    StatusEnum.ORDER_STATUS_FAIL.getMsg()
            );
        }

        return ServerResponse.serverResponseBySuccess(null,null);
    }
}
