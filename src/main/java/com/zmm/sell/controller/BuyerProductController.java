package com.zmm.sell.controller;

import com.zmm.sell.domain.ProductCategory;
import com.zmm.sell.domain.ProductInfo;
import com.zmm.sell.service.ProductCategoryService;
import com.zmm.sell.service.ProductInfoService;
import com.zmm.sell.utils.ResultVoUtil;
import com.zmm.sell.vo.ProductInfoVO;
import com.zmm.sell.vo.ProductVo;
import com.zmm.sell.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Name BuyerProductController
 * @Author 900045
 * @Created by 2019/5/30 0030
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {


	@Autowired
	private ProductInfoService productInfoService;

	@Autowired
	private ProductCategoryService productCategoryService;

	@GetMapping(value = "/list")
	public ResultVo list() {
		// 1. 查询所有的上架商品
		List<ProductInfo> productInfoList = productInfoService.findUpAll();

		// 2. 查询类目(一次性查询)

		//传统方法 ---- new ArrayList<>()对象  通过for循环添加到list集合中

		/**
		 *精简方法
		 */
		List<Integer> categoryTypeList = productInfoList.stream()
				.map(e -> e.getCategoryType())
				.collect(Collectors.toList());
		List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);


		/**
		 * 注意:
		 * 不要把数据库的查询 放入for循环中
		 */
		// 3. 数据封装
		List<ProductVo> productVOList = new ArrayList<>();
		for (ProductCategory productCategory : productCategoryList) {
			ProductVo productVo = new ProductVo();
			productVo.setCategoryType(productCategory.getCategoryType());
			productVo.setCategoryName(productCategory.getCategoryName());


			List<ProductInfoVO> productInfoVOList = new ArrayList<>();
			for (ProductInfo productInfo : productInfoList) {
				if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
					ProductInfoVO productInfoVO = new ProductInfoVO();

					//新方法  相同的属性都会被替换,不管是否有值  (source  target)
					BeanUtils.copyProperties(productInfo, productInfoVO);
					productInfoVOList.add(productInfoVO);
				}
			}
			productVo.setProductInfoVOList(productInfoVOList);
			productVOList.add(productVo);


		}

		/**
		 * 将返回成功的数据封装成方法
		 */
		return ResultVoUtil.success(productVOList);
	}
}
