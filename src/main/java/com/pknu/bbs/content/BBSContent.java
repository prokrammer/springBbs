package com.pknu.bbs.content;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.ui.Model;

public interface BBSContent {
	void content(String pageNum, String articleNum, Model model);
	void delete(String articleNum) throws ServletException, IOException;
	void update(Model model, String articleNum, String title, String content) throws ServletException, IOException;
	void updateForm(String articleNum, Model model) throws ServletException, IOException;
}
