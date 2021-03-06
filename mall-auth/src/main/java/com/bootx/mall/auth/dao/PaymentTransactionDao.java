
package com.bootx.mall.auth.dao;

import java.util.Collection;

import com.bootx.mall.auth.entity.PaymentTransaction;
import com.bootx.mall.auth.plugin.PaymentPlugin;

/**
 * Dao - 支付事务
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface PaymentTransactionDao extends BaseDao<PaymentTransaction, Long> {

	/**
	 * 查找可用支付事务
	 * 
	 * @param lineItem
	 *            支付明细
	 * @param paymentPlugin
	 *            支付插件
	 * @return 可用支付事务，若不存在则返回null
	 */
	PaymentTransaction findAvailable(PaymentTransaction.LineItem lineItem, PaymentPlugin paymentPlugin);

	/**
	 * 查找可用父支付事务
	 * 
	 * @param lineItems
	 *            支付明细
	 * @param paymentPlugin
	 *            支付插件
	 * @return 可用父支付事务，若不存在则返回null
	 */
	PaymentTransaction findAvailableParent(Collection<PaymentTransaction.LineItem> lineItems, PaymentPlugin paymentPlugin);

}