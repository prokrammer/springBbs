package com.pknu.bbs.bbs.login;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pknu.bbs.bbs.dao.BBSDaoImpl;
import com.pknu.bbs.bbs.dao.LoginStatus;

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
		
		
		
		int loginStatus =0;
		String dbPass =bbsDaoImpl.loginCheck(id); 
								
		if(dbPass!=null){
			if(pass.equals(dbPass)){
				loginStatus=LoginStatus.LOGIN_SUCCESS;				
			}else{
				loginStatus=LoginStatus.LOGIN_FAIL;
			}			
		}else{
			loginStatus=LoginStatus.LOGIN_NOTFOUNDID;
		}		
		result = loginStatus;	
		
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
