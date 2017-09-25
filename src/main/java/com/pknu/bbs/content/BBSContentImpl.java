package com.pknu.bbs.content;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.pknu.bbs.dao.BBSDao;
import com.pknu.bbs.dto.BBSDto;
@Service
public class BBSContentImpl implements BBSContent {
	
	@Autowired
	BBSDao bbsdao;
	
	@Override
	public void content(String pageNum, String articleNum, Model model) {
		BBSDto article = new BBSDto();
		try {
			article = bbsdao.getContent(articleNum);
			
			article.setCommentCount((long)bbsdao.commentsCount(Integer.parseInt(articleNum)));
			
			model.addAttribute("article", article);
		} catch (NumberFormatException | SQLException e) {
			e.printStackTrace();
		}
		model.addAttribute("article", article);
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
			e.printStackTrace();
		}
		model.addAttribute("article", article);
	}
	
	@Override
	public void update(Model model, String articleNum, String title, String content) throws ServletException, IOException {
		
		System.out.println(articleNum + title + content);
		HashMap<Object,Object> paramMap = new HashMap<>();
		paramMap.put("articleNum", articleNum);
		paramMap.put("title",title);
		paramMap.put("content", content);
		try {
			bbsdao.getUpdateArticle(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
