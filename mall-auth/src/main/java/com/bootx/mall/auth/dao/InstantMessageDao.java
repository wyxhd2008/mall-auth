
package com.bootx.mall.auth.dao;

import java.util.List;

import com.bootx.mall.auth.common.Filter;
import com.bootx.mall.auth.common.Order;
import com.bootx.mall.auth.common.Page;
import com.bootx.mall.auth.common.Pageable;
import com.bootx.mall.auth.entity.InstantMessage;
import com.bootx.mall.auth.entity.Store;
import com.bootx.mall.auth.entity.InstantMessage.Type;

/**
 * Dao - 即时通讯
 * 
 * @author BOOTX Team
 * @version 6.1
 */
public interface InstantMessageDao extends BaseDao<InstantMessage, Long> {

	/**
	 * 查找即时通讯
	 *
	 * @param type
	 *            类型
	 * @param store
	 *            店铺
	 * @param count
	 *            数量
	 * @param filters
	 *            筛选
	 * @param orders
	 *            排序
	 * @return 即时通讯
	 */
	List<InstantMessage> findList(Type type, Store store, Integer count, List<Filter> filters, List<Order> orders);

	/**
	 * 查找即时通讯分页
	 * 
	 * @param store
	 *            店铺
	 * @param pageable
	 *            分页
	 * @return 即时通讯分页
	 */
	Page<InstantMessage> findPage(Store store, Pageable pageable);

}