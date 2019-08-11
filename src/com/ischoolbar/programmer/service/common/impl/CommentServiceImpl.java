package com.ischoolbar.programmer.service.common.impl;
/**
 * 评论接口实现类
 */
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.dao.common.CommentDao;
import com.ischoolbar.programmer.entity.common.Comment;
import com.ischoolbar.programmer.service.common.CommentService;
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentDao commentDao;
	
	@Override
	public int add(Comment comment) {
		// TODO Auto-generated method stub
		return commentDao.add(comment);
	}

	@Override
	public int edit(Comment comment) {
		// TODO Auto-generated method stub
		return commentDao.edit(comment);
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return commentDao.delete(id);
	}

	@Override
	public List<Comment> findList(Map<String, Object> queMap) {
		// TODO Auto-generated method stub
		return commentDao.findList(queMap);
	}

	@Override
	public Integer getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return commentDao.getTotal(queryMap);
	}

	@Override
	public Comment findById(Long id) {
		// TODO Auto-generated method stub
		return commentDao.findById(id);
	}

}
