package com.remon.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ViewService {

	public HashMap<String, Object> selectGalleryList(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public HashMap<String, Object> readGalleryList(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public HashMap<String, Object> selectGalleryDetail(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
