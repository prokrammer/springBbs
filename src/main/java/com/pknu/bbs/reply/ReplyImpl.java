package com.pknu.bbs.reply;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.pknu.bbs.dao.BBSDao;
import com.pknu.bbs.dto.BBSDto;



@Service
public class ReplyImpl implements BBSReply {
	
	@Autowired
	BBSDao bbsdao;
	
	
	@Override
	public void reply(Model model,String depth, String pos, String groupId, String title, String content, String id) throws ServletException, IOException {
		BBSDto article = new BBSDto();
		
		/*req.setCharacterEncoding("UTF-8");*/
		
		/*String saveDir = req.getServletContext().getInitParameter("saveDir");*/
		
		/*article.setTitle(req.getParameter("title"));
		article.setContent(req.getParameter("content"));
		article.setId((String)req.getSession().getAttribute("id"));
		article.setFname(req.getParameter("fname"));*/
		article.setDepth(Integer.parseInt(/*req.getParameter("depth")*/depth));
		article.setPos(Integer.parseInt(/*req.getParameter("pos")*/pos));
		article.setGroupId(Integer.parseInt(/*req.getParameter("groupId")*/groupId));
		System.out.println(/*req.getParameter("groupId")*/groupId);
		
//		article.setGroupId((int)req.getAttribute("groupId"));
//		article.setDepth((int)req.getAttribute("depth"));
//		article.setPos((int)req.getAttribute("pos"));
		/*return "/list.bbs?pageNum=" + req.getParameter("pageNum");
	}*/
		article.setTitle(/*readParameterValue(req.getPart("title"))*/title);
		article.setContent(/*readParameterValue(req.getPart("content"))*/content);
		article.setId(/*(String)req.getSession().getAttribute("id")*/id);
//		article.setFname(req.getParameter("fname"));
		System.out.println(article);
		//fileInputStream이 fileReader보다 빠를 경우 -> text파일이 아닌 2진 파일일 경우
		/*if(req.getPart("fname").getSize()!=0) {
			Part filePart = req.getPart("fname");
			String originFname = getFileName(filePart);
			article.setFname(originFname);
			
			File file = new File(saveDir + originFname);
			InputStream is = filePart.getInputStream();
			
			FileOutputStream os = new FileOutputStream(file);
			int temp = -1;
			Long time=0l;
			time = System.currentTimeMillis();
			
			while((temp = is.read()) != -1) {
				os.write(temp);
			}
			
			System.out.println("걸린시간 : " +(System.currentTimeMillis()-time));
			
			
			BufferedInputStream bis = new BufferedInputStream(is);
			
			BufferedOutputStream bos = new BufferedOutputStream(os);
			time = System.currentTimeMillis();
			while((temp=bis.read())!=-1) {
				bos.write(temp);
			}
			System.out.println("걸린시간 : " +(time-System.currentTimeMillis()));
			bis.close();
			bos.close();
			 
			is.close();
			os.close();
		}*/
		
		try {
			/*DBCon.getInstance()*/bbsdao.reply(article);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*try {
			DBCon.getInstance().write(article);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		/*return "/list.bbs?pageNum="+req.getParameter("pageNum");*/
	}
	
	/*private String getFileName(Part filePart) {
		String originFname = null;
		for(String cd:filePart.getHeader("content-disposition").split(";")) {
			if(cd.trim().startsWith("filename")) {
				originFname = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return originFname;
	}*/

/*	private String readParameterValue(Part part) throws IOException{
		InputStreamReader reader = new InputStreamReader(part.getInputStream(),"utf-8");

		int temp = -1;
		StringBuffer builder = new StringBuffer();
		while((temp = reader.read())!=-1) {
			//char로 형변환해야 문자로됨
			builder.append((char)temp);
		}
		return builder.toString();
	}*/
}
