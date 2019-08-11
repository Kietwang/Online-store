package com.ischoolbar.programmer.controller.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.entity.common.Product;
import com.ischoolbar.programmer.entity.common.ProductCategory;
import com.ischoolbar.programmer.page.admin.Page;
import com.ischoolbar.programmer.service.common.ProductCategoryService;
import com.ischoolbar.programmer.service.common.ProductService;

/**
 * ��Ʒ���������
 * @author Administrator
 *
 */
@RequestMapping("/admin/product")
@Controller
public class ProductController {
	
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Autowired
	private ProductService productService;
	
	
	/**
	 * ��Ʒ�б�ҳ
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		model.setViewName("product/list");
		model.addObject("productCategoryList", JSONArray.fromObject(productCategoryService.findList(new HashMap<String, Object>())));
		return model;
	}
	
	/**
	 * ��Ʒ���ҳ��
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public ModelAndView add(ModelAndView model){
		model.setViewName("product/add");
		return model;
	}
	
	/**
	 * �༭��Ʒҳ��
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.GET)
	public ModelAndView edit(ModelAndView model,Long id){
		model.setViewName("product/edit");
		model.addObject("product", productService.findById(id));
		return model;
	}
	
	/**
	 * ��ѯ��Ʒ�б�
	 * @param name
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestParam(name="name",defaultValue="")String name,
			@RequestParam(name="productCategoryId",required=false)Long productCategoryId,
			@RequestParam(name="priceMin",required=false)Double priceMin,
			@RequestParam(name="priceMax",required=false)Double priceMax,
				Page page
			){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", name);
		if(productCategoryId != null){
			queryMap.put("tags", productCategoryId);
		}
		if(priceMin != null){
			queryMap.put("priceMin", priceMin);
		}
		if(priceMax != null){
			queryMap.put("priceMax", priceMax);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", productService.findList(queryMap));
		ret.put("total", productService.getTotal(queryMap));
		return ret;
	}
	
	
	/**
	 * �����Ʒ
	 * @param product
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> add(Product product){
		Map<String, Object> ret = new HashMap<String, Object>();
		if(product == null){
			ret.put("type", "error");
			ret.put("msg", "����д��ȷ����Ʒ��Ϣ");
			return ret;
		}
		if(StringUtils.isEmpty(product.getName())){
			ret.put("type", "error");
			ret.put("msg", "����д��Ʒ����");
			return ret;
		}
		if(product.getProductCategoryId() == null){
			ret.put("type", "error");
			ret.put("msg", "��ѡ����������!");
			return ret;
		}
		if(product.getPrice() == null){
			ret.put("type", "error");
			ret.put("msg", "����д��Ʒ�۸�!");
			return ret;
		}
		if(StringUtils.isEmpty(product.getImageUrl())){
			ret.put("type", "error");
			ret.put("msg", "���ϴ���Ʒ��ͼ");
			return ret;
		}
		ProductCategory productCategory = productCategoryService.findById(product.getProductCategoryId());
		product.setTags(productCategory.getTags() + "," + productCategory.getId());
		product.setCreateTime(new Date());
		if(productService.add(product) <= 0){
			ret.put("type", "error");
			ret.put("msg", "���ʧ�ܣ�����ϵ����Ա!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "��ӳɹ�!");
		return ret;
	}
	
	/**
	 * �༭��Ʒ
	 * @param product
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(Product product){
		Map<String, Object> ret = new HashMap<String, Object>();
		if(product == null){
			ret.put("type", "error");
			ret.put("msg", "����д��ȷ����Ʒ��Ϣ");
			return ret;
		}
		if(StringUtils.isEmpty(product.getName())){
			ret.put("type", "error");
			ret.put("msg", "����д��Ʒ����");
			return ret;
		}
		if(product.getProductCategoryId() == null){
			ret.put("type", "error");
			ret.put("msg", "��ѡ����������!");
			return ret;
		}
		if(product.getPrice() == null){
			ret.put("type", "error");
			ret.put("msg", "����д��Ʒ�۸�!");
			return ret;
		}
		if(StringUtils.isEmpty(product.getImageUrl())){
			ret.put("type", "error");
			ret.put("msg", "���ϴ���Ʒ��ͼ");
			return ret;
		}
		ProductCategory productCategory = productCategoryService.findById(product.getProductCategoryId());
		product.setTags(productCategory.getTags() + "," + productCategory.getId());
		if(productService.edit(product) <= 0){
			ret.put("type", "error");
			ret.put("msg", "�༭ʧ�ܣ�����ϵ����Ա!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�༭�ɹ�!");
		return ret;
	}
	
	/**
	 * ɾ����Ʒ
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delete(Long id){
		Map<String, Object> ret = new HashMap<String, Object>();
		if(id == null){
			ret.put("type", "error");
			ret.put("msg", "��ѡ��Ҫɾ����");
			return ret;
		}
		
		try {
			if(productService.delete(id) <= 0){
				ret.put("type", "error");
				ret.put("msg", "ɾ��ʧ�ܣ�����ϵ����Ա!");
				return ret;
			}
		} catch (Exception e) {
			// TODO: handle exception
			ret.put("type", "error");
			ret.put("msg", "����Ʒ�´��ڶ�����Ϣ��������ɾ��!");
			return ret;
		}
		
		ret.put("type", "success");
		ret.put("msg", "ɾ���ɹ�!");
		return ret;
	}
	
	/**
	 * �������η���
	 * @return
	 */
	@RequestMapping(value="/tree_list",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> treeList(){
		Map<String, Object> queryMap = new HashMap<String, Object>();
		return getTreeCategory(productCategoryService.findList(queryMap));
	}
	
	/**
	 * �����б������������ι�ϵ����
	 * @param productCategorieList
	 * @return
	 */
	private List<Map<String, Object>> getTreeCategory(List<ProductCategory> productCategorieList){
		List<Map<String, Object>> ret = new ArrayList<Map<String,Object>>();
		//���еĸ���������
		for(ProductCategory productCategory : productCategorieList){
			if(productCategory.getParentId() == null){
				Map<String, Object> top = new HashMap<String, Object>();
				top.put("id", productCategory.getId());
				top.put("text", productCategory.getName());
				top.put("children", new ArrayList<Map<String,Object>>());
				ret.add(top);
			}
		}
		//��Ӷ���
		for(ProductCategory productCategory : productCategorieList){
			if(productCategory.getParentId() != null){
				for(Map<String, Object> map : ret){
					if(productCategory.getParentId().longValue() == Long.valueOf(map.get("id")+"")){
						List children = (List)map.get("children");
						Map<String, Object> child = new HashMap<String, Object>();
						child.put("id", productCategory.getId());
						child.put("text", productCategory.getName());
						child.put("children", new ArrayList<Map<String,Object>>());
						children.add(child);
					}
				}
			}
		}
		//�������
		for(ProductCategory productCategory : productCategorieList){
			if(productCategory.getParentId() != null){
				for(Map<String, Object> map : ret){
					//��ȡ��������
					List<Map<String, Object>> children = (List<Map<String, Object>>)map.get("children");
					for(Map<String, Object> child : children){
						if(productCategory.getParentId().longValue() == Long.valueOf(child.get("id")+"")){
							List grandsons = (List)child.get("children");
							Map<String, Object> grandson = new HashMap<String, Object>();
							grandson.put("id", productCategory.getId());
							grandson.put("text", productCategory.getName());
							grandsons.add(grandson);
						}
					}
					
				}
			}
		}
		return ret;
	}
}
