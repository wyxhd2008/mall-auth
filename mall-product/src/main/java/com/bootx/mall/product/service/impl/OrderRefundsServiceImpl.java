
package com.bootx.mall.product.service.impl;

import javax.inject.Inject;

import com.bootx.mall.product.dao.SnDao;
import com.bootx.mall.product.entity.OrderRefunds;
import com.bootx.mall.product.entity.Sn;
import com.bootx.mall.product.service.OrderRefundsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service - 订单退款
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Service
public class OrderRefundsServiceImpl extends BaseServiceImpl<OrderRefunds, Long> implements OrderRefundsService {

	@Inject
	private SnDao snDao;

	@Override
	@Transactional
	public OrderRefunds save(OrderRefunds orderRefunds) {
		Assert.notNull(orderRefunds, "[Assertion failed] - orderRefunds is required; it must not be null");

		orderRefunds.setSn(snDao.generate(Sn.Type.ORDER_REFUNDS));

		return super.save(orderRefunds);
	}

}