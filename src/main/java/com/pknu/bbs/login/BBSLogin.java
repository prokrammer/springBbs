package com.pknu.bbs.login;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public interface BBSLogin {
	String loginCheck(HttpServletRequest req) throws SQLException;
}
