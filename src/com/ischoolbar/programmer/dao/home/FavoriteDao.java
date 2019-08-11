package com.ischoolbar.programmer.dao.home;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ischoolbar.programmer.entity.home.Favorite;

/**
 * �ղ�dao��
 * @author Administrator
 *
 */
@Repository
public interface FavoriteDao {
	/**
	 * ����ղ�
	 * @param favorite
	 * @return
	 */
	public int add(Favorite favorite);
	
	
	/**
	 * ɾ���ղ�
	 * @param id
	 * @return
	 */
	public int delete(Long id);
	
	/**
	 * �����������ʲ�ѯ�ղ�
	 * @param queMap
	 * @return
	 */
	public List<Favorite> findList(Map<String, Object> queryMap);
	
	/**
	 * ��ȡ�����������ܼ�¼��
	 * @param queryMap
	 * @return
	 */
	public Integer getTotal(Map<String, Object> queryMap);
	
	/**
	 * ����id��ѯ�ղ�
	 * @param id
	 * @return
	 */
	public Favorite findById(Long id);
	
	/**
	 * ����ids��ѯ�ղ�
	 * @param id
	 * @return
	 */
	public Favorite findByIds(Map<String, Long> queryMap);
}
