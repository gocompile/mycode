package com.lhl.quan.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lhl.entity.Message;
import com.lhl.entity.NoticeParam;
import com.lhl.enums.NoticeType;
import com.lhl.quan.dao.MessageDao;
import com.lhl.quan.dao.NoticeDao;
import com.lhl.quan.dao.UserDao;
import com.lhl.quan.service.MessageService;
import com.lhl.util.FormatAt;
import com.lhl.util.Tools;

public class MessageServiceImpl implements MessageService {

	private MessageDao messageDao;

	private UserDao userDao;

	private NoticeDao noticeDao;

	private final SimpleDateFormat formate = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public void setNoticeDao(NoticeDao noticeDao) {

		this.noticeDao = noticeDao;
	}

	public void setMessageDao(MessageDao messageDao) {

		this.messageDao = messageDao;
	}

	public void setUserDao(UserDao userDao) {

		this.userDao = userDao;
	}

	@Override
	public List<Message> queryMessage(String userId, int offset, int total)
			throws Exception {

		List<Message> list = messageDao.queryMessage(userId, offset, total);
		for (Message message : list) {
			message.setPostTime(Tools.friendly_time(message.getPostTime()));
		}
		return list;
	}

	@Override
	public int getCount(String userId) throws Exception {

		return messageDao.getCount(userId);
	}

	@Override
	public Message addMessage(Message message) throws Exception {

		String content = message.getMessage();
		List<String> referers = new ArrayList<String>();
		String formatContent = FormatAt.getInstance().GenerateRefererLinks(
				userDao, content, referers);
		String subCon = formatContent;
		message.setMessage(subCon);
		message.setPostTime(formate.format(new Date()));
		messageDao.addMessage(message);
		message.setPostTime(Tools.friendly_time(message.getPostTime()));
		int id = message.getId();
		// 启动一个线程发布消息
		NoticeParam noticeParm = new NoticeParam();
		noticeParm.setUserId(message.getUserId());
		// 如果不是二级回复接受人就是楼主，否则就是@的人。
		if (Tools.isEmpty(message.getAtUserId())) {
			noticeParm.setReceiveUserId(message.getUserId());
		} else {
			noticeParm.setReceiveUserId(message.getAtUserId());
		}
		noticeParm.setNoticeType(NoticeType.REMESSAGE);
		noticeParm.setAtUserIds(referers);
		noticeParm.setSendUserId(message.getReUserId());

		if (Tools.isNotEmpty(message.getAtUserId())) {
			noticeParm.setReceiveUserId(message.getAtUserId());
		}
		noticeParm.setReId(id);
		NoticeThread noticeThread = new NoticeThread(noticeParm);
		Thread thread = new Thread(noticeThread);
		thread.start();

		return message;
	}

	public boolean deletMessage(String curUserId, int id) {

		Message message = messageDao.queryMessageById(id);
		if (null != message && message.getUserId().equals(curUserId)) {
			messageDao.deleteMessage(id);
			return true;
		} else {
			return false;
		}
	}
}
