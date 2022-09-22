package com.remon.controller;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.remon.service.ViewService;
import com.remon.util.CommonUtil;

@Controller
public class ViewController {
	private static final Logger log = LoggerFactory.getLogger(ViewController.class);

	ModelAndView mav;
	HttpSession session;
	
	@Autowired
	private ViewService viewServ;

	@RequestMapping(value = "/{serviceId}/{actionId}", method = { RequestMethod.GET, RequestMethod.POST })
	private String viewController(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("serviceId") String serviceId, @PathVariable("actionId") String actionId) throws Exception {
		session = request.getSession();
		String viewStr = serviceId + "/" + actionId; // serviceId로 뷰페이지를 처리한다.
		return viewStr;
	}
	
	@RequestMapping(value = "/detail/{serviceId}/{actionId}/{GALLERY_SEQ}", method = { RequestMethod.GET, RequestMethod.POST })
	private String viewDetailController(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("serviceId") String serviceId, @PathVariable("actionId") String actionId, @PathVariable("GALLERY_SEQ") String GALLERY_SEQ, Model model) throws Exception {
		session = request.getSession();
		String viewStr = serviceId + "/" + actionId; // serviceId로 뷰페이지를 처리한다.
		model.addAttribute("GALLERY_SEQ", GALLERY_SEQ);
		return viewStr;
	}
	
	@RequestMapping(value="/json/{serviceId}/{actionId}",  method = { RequestMethod.GET, RequestMethod.POST })
	public void viewJsonController(HttpServletRequest request, HttpServletResponse response, @PathVariable("serviceId") String serviceId, @PathVariable("actionId") String actionId) throws Exception {
		HashMap<String, Object> returnMap = new HashMap<String, Object>(); // 데이터 리턴객체
		try {
			/**
			 * actionId를 통해 호출해야할 method를 찾을 수 있다.
			 * 이때 해당 method가 받는 인자의 클래스를 전달하여 정확하게 어떤 메소드인지를 찾는다.
			 * classes 배열변수의 인자가 찾으려 하는 메소드(actionID)의 인자와 다를경우 NoSuchMethodExceptoin이 발생한다.
			 */
			Class[] classes = { HttpServletRequest.class, HttpServletResponse.class };
			Method method = viewServ.getClass().getDeclaredMethod(actionId, classes); // method 지정

			// 해당 메소드를 실행한다. method.invoke({해당 메소드를 실행할 클래스객체}, {해당 메소드가 받아야 할 인자});
			Object[] obj = { request, response };
			returnMap = (HashMap<String, Object>) method.invoke(viewServ, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		CommonUtil.response2jsonMap(returnMap, response); // 데이터 리턴
	}
}
