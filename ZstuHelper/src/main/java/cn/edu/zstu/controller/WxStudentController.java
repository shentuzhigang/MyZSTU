package cn.edu.zstu.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.zstu.ApiResponse;
import cn.edu.zstu.Consts;
import cn.edu.zstu.GradeDTO;
import cn.edu.zstu.WeChatTool;
import cn.edu.zstu.ZstuHelperException;
import cn.edu.zstu.beans.Student;
import cn.edu.zstu.service.StudentService;
/**
 * 微信小程序接口
 * @author Andy
 *
 */
@Controller
@RequestMapping("wx")
public class WxStudentController {

	@Autowired
	private StudentService studentService;
	
	@RequestMapping(value="/openid",method= {RequestMethod.GET})
	@ResponseBody
	public Object getOpenid(@RequestParam("code") String code) {	
		ApiResponse ret = new ApiResponse();
		String openid = WeChatTool.getOpenId(code);
		if(!(openid == null || "".equals(openid))) {
			ret.setCode("200");
			ret.setMsg("success");
			ret.setData(openid);
		}
		else {
			ret.setCode("205");
			ret.setMsg("获取失败");
			ret.setData("");
		}
		return ret;
	}
	
	@RequestMapping(value="/courses",method= {RequestMethod.GET})
	@ResponseBody
	public Object getCourses(@RequestParam("openid") String openid) {
		ApiResponse res = Consts.getRetTemp();
		try {
			Student stu = studentService.findStudentById(openid);
			res.setData(studentService.getCourses(stu));
		} catch (ZstuHelperException e) {
			res.setCode(e.getMessage().split("\\|")[0]);
			res.setMsg(e.getMessage().split("\\|")[1]);
		}
		return res;	
	}
	
	@RequestMapping(value="/grades",method={RequestMethod.GET})
	@ResponseBody
	public Object getGrades(@RequestParam("openid") String openid) {
		GradeDTO res = new GradeDTO();
		try {
			Student stu = studentService.findStudentById(openid);
			res = studentService.getGrades(stu);
		} catch (ZstuHelperException e) {
			res.setCode(e.getMessage().split("\\|")[0]);
			res.setMsg(e.getMessage().split("\\|")[1]);
		}
		return res;
	}
	
	@RequestMapping(value="/exams",method= {RequestMethod.GET})
	@ResponseBody
	public Object getExams(@RequestParam("openid") String openid){
		ApiResponse res = Consts.getRetTemp();
		try {
			Student stu = studentService.findStudentById(openid);
			res.setData(studentService.getExams(stu));
		} catch (ZstuHelperException e) {
			res.setCode(e.getMessage().split("\\|")[0]);
			res.setMsg(e.getMessage().split("\\|")[1]);
		}
		return res;
	}
	
	@RequestMapping(value="/cardinfo",method= {RequestMethod.GET})
	@ResponseBody
	public Object getConsumption(@RequestParam("openid") String openid,@RequestParam("start") String start,@RequestParam("end") String end){	
		ApiResponse res = Consts.getRetTemp();
		try {
			Student stu = studentService.findStudentById(openid);
			res.setData(studentService.getCardInfo(stu, start, end));	
		} catch (ZstuHelperException e) {
			res.setCode(e.getMessage().split("\\|")[0]);
			res.setMsg(e.getMessage().split("\\|")[1]);
		}
		return res;
	}
	
	@RequestMapping(value="/borrows",method= {RequestMethod.GET})
	@ResponseBody
	public Object getBorrowList(@RequestParam("openid") String openid){	
		ApiResponse res = Consts.getRetTemp();
		try {
			Student stu = studentService.findStudentById(openid);
			res.setData(studentService.getBookRecord(stu));		
		} catch (ZstuHelperException e) {
			res.setCode(e.getMessage().split("\\|")[0]);
			res.setMsg(e.getMessage().split("\\|")[1]);
		}
		return res;
	}
	
	@RequestMapping(value="/book",method= {RequestMethod.GET})
	@ResponseBody
	public Object getBookInfo(@RequestParam("key") String key) {	
		ApiResponse res = Consts.getRetTemp();
		res.setData(studentService.getBooks(key));
		return res;
	}
	
	@RequestMapping(value="/firstday",method= {RequestMethod.GET})
	@ResponseBody
	public Object getFirstDay(){	
		ApiResponse res = Consts.getRetTemp();
		try {
			res.setData(Consts.getFirstDay());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	@RequestMapping(value="/phones",method= {RequestMethod.GET})
	@ResponseBody
	public Object getPhones(){	
		ApiResponse res = Consts.getRetTemp();
		try {
			res.setData(Consts.getPhones());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;	
	}
	
	@RequestMapping(value="/student",method= {RequestMethod.POST})
	@ResponseBody
	public Object addStudent(@RequestParam("openid") String openid,@RequestParam("sid") String sid,
			@RequestParam("edupw") String edupw,@RequestParam("ecardpw") String ecardpw) {
		ApiResponse ret = Consts.getRetTemp();
		Student stu = new Student();
		stu.setOpenid(openid);
		stu.setSid(sid);
		stu.setEcardPw(ecardpw);
		stu.setEduPw(edupw);
		this.studentService.saveStudent(stu);
		return ret;
	}
}

