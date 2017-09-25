package com.pknu.bbs.bbs.write;

import java.io.IOException;

import javax.servlet.ServletException;

import com.pknu.bbs.bbs.dto.BBSDto;

public interface BBSWrite {
	String write(BBSDto article) throws ServletException, IOException;
}
