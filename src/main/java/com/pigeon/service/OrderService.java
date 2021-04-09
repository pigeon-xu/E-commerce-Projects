package com.pigeon.service;

import com.pigeon.error.BusinessException;
import com.pigeon.service.model.OrderModel;
import org.springframework.stereotype.Service;

public interface OrderService {

    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException;
}
