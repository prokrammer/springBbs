package com.pknu.bbs.bbs.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.pknu.bbs.bbs.common.Page;
import com.pknu.bbs.bbs.dao.BBSDao;
import com.pknu.bbs.bbs.dto.BBSDto;
import com.pknu.bbs.comment.dao.CommentDao;

@Service
public class BBSServiceImpl implements BBSService {
	@Autowired
	BBSDao bbsDao;
	
//	@Autowired
//	CommentDao commentDao;
//	
	@Autowired //type�� Page�� ���� DI���ش�
	Page page;
	
	
	@Resource(name="pageSize") //type�� String�� ���� DI���ش� ���߿��� id->name->class ������ "pageSize"�� �༮�� DI�Ѵ�.
	Integer pageSize;
	
	@Resource(name="pageBlock")
	Integer pageBlock;
	
	
	@Override
	public void list(int pageNum, Model model) {
		int totalCount=0;
		ArrayList<BBSDto> articleList=null;
		HashMap<String, String> pagingMap=null;
		
		
		
		
		
		try {
			totalCount = bbsDao.getTotalCount();
			
			pagingMap = page.paging(pageNum, totalCount, pageSize, pageBlock);
			
			int startRow = page.getStartRow();
			int endRow = page.getEndRow();
			HashMap<Object,Object> paramMap = new HashMap<>();
			paramMap.put("startRow", startRow);
			paramMap.put("endRow", endRow);
			
			articleList = (ArrayList<BBSDto>)bbsDao.getArticleList(paramMap);
			
			for(BBSDto bbsdto:articleList) {
				bbsdto.setCommentCount((long)bbsDao.commentsCount(bbsdto.getArticleNum()));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		model.addAttribute("totalCount",totalCount);
		model.addAttribute("articleList",articleList);
		model.addAttribute("pageCode",pagingMap.get("pageCode"));
	
	}
	
	

}
