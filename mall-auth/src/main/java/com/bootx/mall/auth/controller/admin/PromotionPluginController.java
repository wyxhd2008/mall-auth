
package com.bootx.mall.auth.controller.admin;

import javax.inject.Inject;

import com.bootx.mall.auth.service.PluginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller - 促销插件
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("adminPromotionPluginController")
@RequestMapping("/admin/promotion_plugin")
public class PromotionPluginController extends BaseController {

	@Inject
	private PluginService pluginService;

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(ModelMap model) {
		model.addAttribute("promotionPlugins", pluginService.getPromotionPlugins());
		return "admin/promotion_plugin/list";
	}

}