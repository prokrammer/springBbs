package com.pknu.bbs.comment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BBSComment {
	void read(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
	void write(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
