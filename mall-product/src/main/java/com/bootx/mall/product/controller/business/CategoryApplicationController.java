
package com.bootx.mall.product.controller.business;

import java.util.List;

import javax.inject.Inject;

import com.bootx.mall.product.common.Pageable;
import com.bootx.mall.product.common.Results;
import com.bootx.mall.product.entity.CategoryApplication;
import com.bootx.mall.product.entity.ProductCategory;
import com.bootx.mall.product.entity.Store;
import com.bootx.mall.product.service.CategoryApplicationService;
import com.bootx.mall.product.service.ProductCategoryService;
import com.bootx.mall.product.security.CurrentStore;
import com.bootx.mall.product.service.StoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller - 经营分类申请
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Controller("businessCategoryApplicationController")
@RequestMapping("/business/category_application")
public class CategoryApplicationController extends BaseController {

	@Inject
	private CategoryApplicationService categoryApplicationService;
	@Inject
	private ProductCategoryService productCategoryService;
	@Inject
	private StoreService storeService;

	/**
	 * 添加
	 */
	@GetMapping("/add")
	public String add(@CurrentStore Store currentStore, ModelMap model) {
		if (currentStore == null) {
			return UNPROCESSABLE_ENTITY_VIEW;
		}

		List<ProductCategory> productCategories = storeService.findProductCategoryList(currentStore, CategoryApplication.Status.PENDING);
		model.addAttribute("productCategoryTree", productCategoryService.findTree());
		model.addAttribute("appliedProductCategories", productCategories);
		return "business/category_application/add";
	}

	/**
	 * 保存
	 */
	@PostMapping("/save")
	public ResponseEntity<?> save(Long productCategoryId, @CurrentStore Store currentStore) {
		ProductCategory productCategory = productCategoryService.find(productCategoryId);
		if (productCategory == null || currentStore == null) {
			return Results.UNPROCESSABLE_ENTITY;
		}
		if (storeService.productCategoryExists(currentStore, productCategory) || categoryApplicationService.exist(currentStore, productCategory, CategoryApplication.Status.PENDING)) {
			return Results.UNPROCESSABLE_ENTITY;
		}

		CategoryApplication categoryApplication = new CategoryApplication();
		categoryApplication.setStatus(CategoryApplication.Status.PENDING);
		categoryApplication.setRate(Store.Type.GENERAL.equals(currentStore.getType()) ? productCategory.getGeneralRate() : productCategory.getSelfRate());
		categoryApplication.setStore(currentStore);
		categoryApplication.setProductCategory(productCategory);
		categoryApplicationService.save(categoryApplication);

		return Results.OK;
	}

	/**
	 * 列表
	 */
	@GetMapping("/list")
	public String list(Pageable pageable, @CurrentStore Store currentStore, ModelMap model) {
		model.addAttribute("page", categoryApplicationService.findPage(null, currentStore, null, pageable));
		return "business/category_application/list";
	}

}