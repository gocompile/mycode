package com.lhl.quan.service.impl;

import java.util.List;

import com.lhl.entity.BlogItem;
import com.lhl.quan.dao.BlogItemDao;
import com.lhl.quan.service.BlogItemService;

public class BlogItemServiceImpl implements BlogItemService
{

	private BlogItemDao blogItemDao;

	public void setBlogItemDao(BlogItemDao blogItemDao)
	{

		this.blogItemDao = blogItemDao;
	}

	@Override
	public int saveItem(BlogItem item)
	{

		int id = item.getId();
		if (item.getId() != 0)
		{
			blogItemDao.update(item);
		}
		else
		{
			id = blogItemDao.addItem(item);
		}
		return id;
	}

	@Override
	public boolean delete(String userId, int id)
	{

		if (isCurUser(userId, id))
		{
			blogItemDao.delete(id);
			return true;
		}
		else
		{
			return false;
		}

	}

	@Override
	public List<BlogItem> queryBlogItemByUserId(String userId)
	{

		return blogItemDao.queryItemByUserId(userId);
	}

	@Override
	public List<BlogItem> queryBlogItemAndCountByUserId(String userId)
	{

		return blogItemDao.queryBlogItemAndCountByUserId(userId);
	}

	private boolean isCurUser(String userId, int id)
	{

		BlogItem item = blogItemDao.queryBlogItemById(id);
		if (item != null && item.getUserId().equals(userId))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public BlogItem queryBlogItemById(int itemId)
	{

		return blogItemDao.queryBlogItemById(itemId);
	}

}
