package com.pknu.bbs.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.ui.Model;

import com.pknu.bbs.dto.BBSDto;

public interface BBSService {
	public void list(int pageNum, Model model);
}
