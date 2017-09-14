package com.pknu.bbs.reply;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.ui.Model;

public interface BBSReply {
	void reply(Model model, String depth, String pos, String groupId, String title, String content, String id)
			throws ServletException, IOException;
}
