package com.pknu.bbs.content;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.pknu.bbs.dao.BBSDao;
import com.pknu.bbs.dto.BBSDto;
import com.pknu.bbs.login.BBSLogin;
@Service
public class BBSContentImpl implements BBSContent {
	
	@Autowired
	BBSDao bbsdao;
	
	@Override
	public void content(String pageNum, String articleNum, Model model) {
		StringBuffer view = new StringBuffer();
		BBSDto bbsdto = new BBSDto();
		try {
			bbsdto = bbsdao.getContent(articleNum);
			model.addAttribute("article", bbsdto);
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("article", bbsdto);
		model.addAttribute("pageNum", pageNum);
	}
	
	@Override
	public void delete(String articleNum) throws ServletException, IOException {
		try {
			bbsdao.delete(articleNum);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void updateForm(String articleNum, Model model) throws ServletException, IOException {
		
		BBSDto article=null;
		try {
			article=bbsdao.getUpdateArticle(articleNum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("article", article);
	}
	
	@Override
	public void update(Model model, String articleNum, String title, String content) throws ServletException, IOException {
		
		try {
			bbsdao.getUpdateArticle(articleNum,title,content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
