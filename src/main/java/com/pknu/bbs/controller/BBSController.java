package com.pknu.bbs.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pknu.bbs.comment.BBSComment;
import com.pknu.bbs.content.BBSContent;
import com.pknu.bbs.dao.BBSDao;
import com.pknu.bbs.dao.BBSDaoImpl;
import com.pknu.bbs.dao.LoginStatus;
import com.pknu.bbs.dto.BBSDto;
import com.pknu.bbs.join.BBSJoin;
import com.pknu.bbs.login.BBSLogin;
import com.pknu.bbs.reply.BBSReply;
import com.pknu.bbs.service.BBSService;
import com.pknu.bbs.write.BBSWrite;

@Controller
public class BBSController {
	
	@Autowired
	private BBSService bbsService;
	
	@Autowired
	private BBSLogin bbsLogin;
	
	@Autowired
	private BBSContent bbscontent;
	
	@Autowired
	private BBSWrite bbswrite;
	
	@Autowired
	private BBSJoin bbsjoin;
	
	@Autowired
	private BBSComment bbscomment;
	
	@Autowired
	private BBSReply bbsreply;
	
	@RequestMapping(value="/list.bbs")
		public String list(String pageNum, Model model) {
		/*model.addAttribute("articleList",bbsService.getArticles(pageNum));
		model.addAttribute("totlaCount",bbsService.getTotalCount());
		
		model.addAttribute("pageCode",bbsService.getArticles(pageNum));
		
		model.addAttribute("pageNum",pageNum);*/
		bbsService.list(pageNum, model);
		model.addAttribute("pageNum",pageNum);
		return "list";
		
	}
	
	@RequestMapping(value="/joinForm.bbs")
	public String joinForm(String pageNum, Model model) {
		model.addAttribute("pageNum", pageNum);
		
		return "joinform";
	}
	
	@RequestMapping(value="/join.bbs")
	public String join(Model model,String id, String pass) {
		
		bbsjoin.join(model,id,pass);
		return "redirect:list.bbs?pageNum=1";
	}
	
	@RequestMapping(value="/writeForm.bbs")
	public String writeForm(HttpSession session, HttpServletRequest req) {
		if((String)session.getAttribute("id")==null){
			req.setAttribute("pageNum", "1");
			return "login";
		}
		return "writeForm";
	}
	
	@RequestMapping(value="/write.bbs")
	public String write(HttpServletRequest req) {
		try {
			bbswrite.write(req);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:list.bbs?pageNum=1";
	}
	@RequestMapping(value="/content.bbs")
	public String content(String pageNum, String articleNum, Model model) {
		bbscontent.content(pageNum, articleNum, model);
		return "content";
	}
	@RequestMapping(value="/delete.bbs")
	public String delete(String articleNum,String pageNum) {
			try {
				bbscontent.delete(articleNum);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		
		return "redirect:list.bbs?pageNum="+pageNum;
	}
	
	@RequestMapping(value="/updateForm.bbs")
	public String updateForm(String articleNum, String pageNum, Model model) {
		model.addAttribute("articleNum", articleNum);
		model.addAttribute("pageNum", pageNum);
		try {
			bbscontent.updateForm(articleNum, model);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
		return "updateForm";
	}
	
	@RequestMapping(value="/update.bbs")
	public String update(Model model, String pageNum, String articleNum, String title, String content) {
		try {
			bbscontent.update(model, articleNum, title, content);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:list.bbs?pageNum="+pageNum;
	}
	
	@RequestMapping(value="/replyForm.bbs")
	public String replyForm(HttpServletRequest req, String pageNum, String depth, String pos, String groupId) {
		BBSDto bd = new BBSDto();
		bd.setDepth(Integer.parseInt(/*req.getParameter("depth")*/depth));
		bd.setPos(Integer.parseInt(/*req.getParameter("pos")*/pos));
		bd.setGroupId(Integer.parseInt(/*req.getParameter("groupId")*/groupId));
		req.setAttribute("replyDto", bd);
		req.setAttribute("pageNum", pageNum);
		System.out.println("µª½º : " + bd.getDepth());
		System.out.println(bd);
		return "replyForm"; 
	}
	
	@RequestMapping(value="/reply.bbs", method=RequestMethod.POST)
	public String reply(String pageNum, Model model, String depth, String pos, String groupId, String title, String content, HttpSession session) {
		String id = (String)session.getAttribute("id");
		try {
			bbsreply.reply(model, depth, pos, groupId, title, content, id);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:list.bbs?pageNum="+pageNum;
	}
	
	@RequestMapping(value="/login.bbs")
	public String login(HttpServletRequest req) {
			
		String view=null;
		try {
			view = bbsLogin.loginCheck(req);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return view;
	}
	
	@RequestMapping(value="/commentRead.bbs")
	public String commentRead(HttpServletRequest req, HttpServletResponse resp) {
		try {
			bbscomment.read(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/commentWrite.bbs")
	public String commentWrite(HttpServletRequest req, HttpServletResponse resp) {
		try {
			bbscomment.write(req, resp);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/logout.bbs")
	public String logout(HttpSession session, String pageNum) {
		/*req.getSession().setAttribute("id", null);*/
		session.invalidate();
		return "redirect:list.bbs?pageNum="+pageNum;
	}

}
