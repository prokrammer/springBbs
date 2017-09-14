package com.pknu.bbs.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.pknu.bbs.common.Page;
import com.pknu.bbs.dao.BBSDao;
import com.pknu.bbs.dto.BBSDto;

@Service
public class BBSServiceImpl implements BBSService {
	@Autowired
	BBSDao bbsDao;
	
	@Autowired //type�� Page�� ���� DI���ش�
	Page page;
	
	
	@Resource(name="pageSize") //type�� String�� ���� DI���ش� ���߿��� id->name->class ������ "pageSize"�� �༮�� DI�Ѵ�.
	Integer pageSize;
	
	@Resource(name="pageBlock")
	Integer pageBlock;
	
	
	@Override
	public void list(String pageNum, Model model) {
		int totalCount=0;
		ArrayList<BBSDto> articleList=null;
		HashMap<String, String> pagingMap=null;
		
		try {
			totalCount = bbsDao.getTotalCount();
			pagingMap = page.paging(Integer.parseInt(pageNum), totalCount, pageSize, pageBlock);
			articleList = (ArrayList<BBSDto>)bbsDao.getArticleList(page.getStartRow(),page.getEndRow());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("totalCount",totalCount);
		model.addAttribute("articleList",articleList);
		model.addAttribute("pageCode",pagingMap.get("pageCode"));
	
	}
	
	

}
