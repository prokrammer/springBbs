package com.pknu.bbs.write;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface BBSWrite {
	String write(HttpServletRequest req) throws ServletException, IOException;
}
