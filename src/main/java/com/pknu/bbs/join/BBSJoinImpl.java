package com.pknu.bbs.join;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.pknu.bbs.dao.BBSDao;

@Service
public class BBSJoinImpl implements BBSJoin {

	@Autowired
	BBSDao bbsdao;
	
	@Override
	public void join(Model model,String id, String pass) {
		
		String result = null;
		try {
			result = bbsdao.join(id, pass);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("result", result);
		
	}
	
}
