package com.mfz.function.testfunction.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mfz.function.testfunction.mapper.TestMapper;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private TestMapper testMapper;
	
	@RequestMapping("/testRes")
	@ResponseBody
	public Map testRes(HttpServletRequest request,Model model){
		Test test  = new Test();

		test.sayHello("","");
		Map retMap = new HashMap();
		retMap.put("status", "000");
		retMap.put("msg", "返回成功1111");
		return retMap;
	}
	
	@RequestMapping("/testGetList")
	@ResponseBody
	public List testGetList(HttpServletRequest request,Model model){
		Map parmMap = new HashMap();
		parmMap.put("ORG_CODE", "11610201");
		return testMapper.getUserList(parmMap);
	}
	
	@RequestMapping("/testPageGetList")
	@ResponseBody
	public List testPageGetList(HttpServletRequest request,Model model){
		Map parmMap = new HashMap();
		parmMap.put("ORG_CODE", "11610201");
		PageHelper.startPage(1, 5);
		List retList =  testMapper.getUserList(parmMap);
		System.out.println("total count ==" + 	((Page) retList).getTotal());
		return retList;
	}

	@RequestMapping("/updateRecord")
	@ResponseBody
	public String insertRecord(HttpServletRequest request,Model model){
		String name  = request.getParameter("name");
		if(name == null){
			return "参数name不能为空";
		}
		Map parmMap = new HashMap();
		parmMap.put("USER_NAME",name);
		int count = testMapper.updateRecord(parmMap);

		return "影响记录数为："+count;
	}

}
