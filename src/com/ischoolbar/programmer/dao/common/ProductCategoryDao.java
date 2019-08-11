package com.ischoolbar.programmer.dao.common;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ischoolbar.programmer.entity.common.ProductCategory;

/**
 * ��Ʒ����dao��
 * @author Administrator
 *
 */
@Repository
public interface ProductCategoryDao {
	/**
	 * �����Ʒ����
	 * @param productCategory
	 * @return
	 */
	public int add(ProductCategory productCategory);
	
	/**
	 * �༭��Ʒ����
	 * @param productCategory
	 * @return
	 */
	public int edit(ProductCategory productCategory);
	
	/**
	 * ɾ����Ʒ����
	 * @param id
	 * @return
	 */
	public int delete(Long id);
	
	/**
	 * �����������ʲ�ѯ��Ʒ����
	 * @param queMap
	 * @return
	 */
	public List<ProductCategory> findList(Map<String, Object> queMap);
	
	/**
	 * ��ȡ�����������ܼ�¼��
	 * @param queryMap
	 * @return
	 */
	public Integer getTotal(Map<String, Object> queryMap);
	
	/**
	 * ����id��ѯ��Ʒ����
	 * @param id
	 * @return
	 */
	public ProductCategory findById(Long id);
}
