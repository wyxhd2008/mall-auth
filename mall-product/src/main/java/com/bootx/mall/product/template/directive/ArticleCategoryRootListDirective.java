
package com.bootx.mall.product.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.bootx.mall.product.entity.ArticleCategory;
import com.bootx.mall.product.service.ArticleCategoryService;
import org.springframework.stereotype.Component;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 模板指令 - 顶级文章分类列表
 * 
 * @author BOOTX Team
 * @version 6.1
 */
@Component
public class ArticleCategoryRootListDirective extends BaseDirective {

	/**
	 * 变量名称
	 */
	private static final String VARIABLE_NAME = "articleCategories";

	@Inject
	private ArticleCategoryService articleCategoryService;

	public static ArticleCategoryRootListDirective articleCategoryRootListDirective;

	@PostConstruct
	public void init() {
		articleCategoryRootListDirective = this;
		articleCategoryRootListDirective.articleCategoryService = this.articleCategoryService;
	}

	/**
	 * 执行
	 * 
	 * @param env
	 *            环境变量
	 * @param params
	 *            参数
	 * @param loopVars
	 *            循环变量
	 * @param body
	 *            模板内容
	 */
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		Integer count = getCount(params);
		boolean useCache = useCache(params);

		List<ArticleCategory> articleCategories = articleCategoryService.findRoots(count, useCache);
		setLocalVariable(VARIABLE_NAME, articleCategories, env, body);
	}

}