package com.pknu.bbs.login;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pknu.bbs.dao.BBSDaoImpl;
import com.pknu.bbs.dao.LoginStatus;

@Service
public class BBSLoginImpl implements BBSLogin {
	
	@Autowired
	BBSDaoImpl bbsDaoImpl;
	
	@Override
	public String loginCheck(HttpServletRequest req) throws SQLException {
		String id = req.getParameter("id");
		String pass = req.getParameter("pass");
		String pageNum = req.getParameter("pageNum");
		System.out.println(pageNum);
		int result = 0;
		String view = null;
			
		result = bbsDaoImpl.loginCheck(id, pass);
		System.out.println(result);
		if(result==LoginStatus.LOGIN_SUCCESS) {
			req.getSession().setAttribute("id", id);
			view="redirect:list.bbs?pageNum="+pageNum;
		} else if(result==LoginStatus.LOGIN_FAIL){
			view="login";
		} else {
			view="join";
		}

		return view;
	}


}
