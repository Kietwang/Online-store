package com.ischoolbar.programmer.controller.admin;

import java.util.HashMap;
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

import com.ischoolbar.programmer.entity.common.Account;
import com.ischoolbar.programmer.entity.common.Order;
import com.ischoolbar.programmer.page.admin.Page;
import com.ischoolbar.programmer.service.common.AccountService;
import com.ischoolbar.programmer.service.common.OrderService;

/**
 * �������������
 * @author Administrator
 *
 */
@RequestMapping("/admin/order")
@Controller
public class OrderController {
	
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AccountService accountService;
	
	
	/**
	 * �����б�ҳ
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		model.setViewName("order/list");
		model.addObject("accountList", JSONArray.fromObject(accountService.findList(new HashMap<String, Object>())));
		return model;
	}
	
	
	/**
	 * ��ѯ�����б�
	 * @param sn
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> list(@RequestParam(name="sn",defaultValue="")String sn,
			@RequestParam(name="username",required=false)String username,
			@RequestParam(name="moneyMin",required=false)Double moneyMin,
			@RequestParam(name="moneyMax",required=false)Double moneyMax,
			@RequestParam(name="status",required=false)Integer status,
				Page page
			){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("sn", sn);
		if(!StringUtils.isEmpty(username)){
			Account account = accountService.findByName(username);
			if(account != null){
				queryMap.put("userId", account.getId());
			}
		}
		if(moneyMin != null){
			queryMap.put("moneyMin", moneyMin);
		}
		if(moneyMax != null){
			queryMap.put("moneyMax", moneyMax);
		}
		if(status != null){
			queryMap.put("status", status);
		}
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", orderService.findList(queryMap));
		ret.put("total", orderService.getTotal(queryMap));
		return ret;
	}
	
	/**
	 * ���ݶ���id��ѯ��������
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value="/get_item_list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> itemList(Long orderId){
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("rows", orderService.findOrderItemList(orderId));
		return ret;
	}
	
	/**
	 * �༭����
	 * @param order
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> edit(Order order){
		Map<String, Object> ret = new HashMap<String, Object>();
		if(order == null){
			ret.put("type", "error");
			ret.put("msg", "��ѡ����ȷ�Ķ�����Ϣ");
			return ret;
		}
		if(StringUtils.isEmpty(order.getAddress())){
			ret.put("type", "error");
			ret.put("msg", "����д�����ջ���ַ");
			return ret;
		}
		if(order.getMoney() == null){
			ret.put("type", "error");
			ret.put("msg", "����д�������!");
			return ret;
		}
		if(orderService.edit(order) <= 0){
			ret.put("type", "error");
			ret.put("msg", "�༭ʧ�ܣ�����ϵ����Ա!");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�༭�ɹ�!");
		return ret;
	}
	
	
}
