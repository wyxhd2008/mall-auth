
package com.bootx.mall.auth.controller.admin.plugin;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.bootx.mall.auth.common.Results;
import com.bootx.mall.auth.controller.admin.BaseController;
import com.bootx.mall.auth.entity.PluginConfig;
import com.bootx.mall.auth.plugin.PaymentPlugin;
import com.bootx.mall.auth.plugin.WeixinPublicPaymentPlugin;
import com.bootx.mall.auth.service.PluginConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller - 微信支付(公众号支付)
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("adminPluginWeixinPublicPaymentController")
@RequestMapping("/admin/plugin/weixin_public_payment")
public class WeixinPublicPaymentController extends BaseController {

	@Inject
	private WeixinPublicPaymentPlugin weixinPublicPaymentPlugin;
	@Inject
	private PluginConfigService pluginConfigService;

	/**
	 * 安装
	 */
	@PostMapping("/install")
	public ResponseEntity<?> install() {
		if (!weixinPublicPaymentPlugin.getIsInstalled()) {
			PluginConfig pluginConfig = new PluginConfig();
			pluginConfig.setPluginId(weixinPublicPaymentPlugin.getId());
			pluginConfig.setIsEnabled(false);
			pluginConfig.setAttributes(null);
			pluginConfigService.save(pluginConfig);
		}
		return Results.OK;
	}

	/**
	 * 卸载
	 */
	@PostMapping("/uninstall")
	public ResponseEntity<?> uninstall() {
		if (weixinPublicPaymentPlugin.getIsInstalled()) {
			pluginConfigService.deleteByPluginId(weixinPublicPaymentPlugin.getId());
		}
		return Results.OK;
	}

	/**
	 * 设置
	 */
	@GetMapping("/setting")
	public String setting(ModelMap model) {
		PluginConfig pluginConfig = weixinPublicPaymentPlugin.getPluginConfig();
		model.addAttribute("feeTypes", PaymentPlugin.FeeType.values());
		model.addAttribute("pluginConfig", pluginConfig);
		return "/admin/plugin/weixin_public_payment/setting";
	}

	/**
	 * 更新
	 */
	@PostMapping("/update")
	public ResponseEntity<?> update(String displayName, String appId, String appSecret, String mchId, String apiKey, PaymentPlugin.FeeType feeType, BigDecimal fee, String logo, String description, @RequestParam(defaultValue = "false") Boolean isEnabled, Integer order) {
		PluginConfig pluginConfig = weixinPublicPaymentPlugin.getPluginConfig();
		Map<String, String> attributes = new HashMap<>();
		attributes.put(PaymentPlugin.DISPLAY_NAME_ATTRIBUTE_NAME, displayName);
		attributes.put("appId", appId);
		attributes.put("appSecret", appSecret);
		attributes.put("mchId", mchId);
		attributes.put("apiKey", apiKey);
		attributes.put(PaymentPlugin.FEE_TYPE_ATTRIBUTE_NAME, String.valueOf(feeType));
		attributes.put(PaymentPlugin.FEE_ATTRIBUTE_NAME, String.valueOf(fee));
		attributes.put(PaymentPlugin.LOGO_ATTRIBUTE_NAME, logo);
		attributes.put(PaymentPlugin.DESCRIPTION_ATTRIBUTE_NAME, description);
		pluginConfig.setAttributes(attributes);
		pluginConfig.setIsEnabled(isEnabled);
		pluginConfig.setOrder(order);
		pluginConfigService.update(pluginConfig);
		return Results.OK;
	}

}