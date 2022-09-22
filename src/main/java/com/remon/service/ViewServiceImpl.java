package com.remon.service;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.remon.controller.ViewController;
import com.remon.dao.ViewDao;
import com.remon.util.CommonUtil;

@Service("ViewService")
public class ViewServiceImpl implements ViewService{
	
	private static final Logger log = LoggerFactory.getLogger(ViewServiceImpl.class);
	
	@Autowired
	private ViewDao dao;
	
	@Autowired
	ServletContext servCont;

//	@Value("#{global['FILE_LOCATION']}")
//	private String FILE_PATH;

	private static String FILE_PATH = "C:\\OneDrive\\pic";

	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/**
	 * 파일 목록 읽기
	 */
	@Override
	public HashMap<String, Object> selectGalleryList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("FILE_PATH:::"+FILE_PATH);
		HashMap<String, Object> paramMap = CommonUtil.request2mapObject(request);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		int pageNum = CommonUtil.nvl(paramMap.get("PAGE_NUM"), 1);
		int pageSize = CommonUtil.nvl(paramMap.get("PAGE_SIZE"), 40);
		int pageIdx = (pageNum - 1) * pageSize;
		paramMap.put("PAGE_NUM", String.valueOf(pageIdx));
		paramMap.put("PAGE_SIZE", String.valueOf(pageSize));
		paramMap.put("ORDER", (CommonUtil.nvl(paramMap.get("ORDER"), "AUTHOR")) );
		paramMap.put("ORDER_FLAG", (CommonUtil.nvl(paramMap.get("ORDER_FLAG"), "DESC")) );
		
		if(!"".equals( String.valueOf(paramMap.get("ORDER_RN") )) &&    !"null".equals( String.valueOf(paramMap.get("ORDER_RN") ))     ) {
			if("DESC".equals(paramMap.get("ORDER_FLAG"))) {
				paramMap.put("ORDER_RN", "ASC");
			} else {
				paramMap.put("ORDER_RN", "DESC");
			}
		} else {
			paramMap.put("ORDER_RN", "ASC");
		}
		
		// paramMap.put("ORDER_RN", (CommonUtil.nvl(paramMap.get("ORDER_RN"), "ASC")) );

		List<HashMap<String, Object>> resultList = dao.selectGalleryList(paramMap);
		
		int totalCount = dao.selectGalleryCnt(paramMap);
		
		String pagination = CommonUtil.makePagenationMax(totalCount, pageNum, pageSize, 10, (String)paramMap.get("pageScript"));
		
		if(resultList != null && resultList.size() > 0) {
			resultMap.put("resultList", resultList);
			resultMap.put("pagination", pagination);
		}
	
		return resultMap;
	}
	/**
	 * 파일 읽기
	 */
	@Override
	public HashMap<String, Object> readGalleryList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, Object> titleMap = new HashMap<String, Object>();
		
		String folderNm = "";
		String oriFileNm = ""; // 원본 파일 이름
		String extNm = ""; // 확장자 이름
		int result = 0;
		int successCnt = 0;
		int gallerySeq = 0;
		long lastTime= 0;
		File dir = new File(FILE_PATH);
		File[] filesArr = dir.listFiles();
		boolean titleBoolean = false;
		String pattern = "yyyyMMddHHmmss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);   
		List<HashMap<String, Object>> authList = dao.selectGalleryAuthList();
		try {
			for(int i = 0; i < filesArr.length; i++) {
				titleBoolean = false;
				folderNm = filesArr[i].getName();
				
				lastTime = filesArr[i].lastModified();
				Date lastModifiedDate = new Date( lastTime );
				
				titleMap.put("REG_DATE", simpleDateFormat.format(lastModifiedDate));
				// 폴더명 DB처리
				titleMap.put("AUTHOR", folderNm);
				File imgDir = new File(filesArr[i].toString());
				File[] imgDirArr = imgDir.listFiles();
				for (HashMap<String, Object> authMap : authList) {
					if(folderNm.equals((String)authMap.get("AUTHOR"))) {
						if(imgDirArr.length > Integer.valueOf(String.valueOf(authMap.get("CNT"))) || imgDirArr.length < Integer.valueOf(String.valueOf(authMap.get("CNT"))) ) {
							System.out.println("size2:::"+Integer.valueOf(String.valueOf(authMap.get("CNT"))));
							System.out.println("zz:"+(String)authMap.get("AUTHOR"));
							dao.deleteGalleryDetail(authMap);
							dao.deleteGallery(authMap);
						} else {
							titleBoolean = true;
							continue;	
						}
					}
				}
				
				if(titleBoolean) {
					continue;
				}
				
				gallerySeq = dao.selectGallerySeq();
				titleMap.put("GALLERY_SEQ", gallerySeq);
				result = dao.insertGallery(titleMap);
				if(result < 1) {
					continue;
				}
				try {
					// 파일 처리
					if( folderNm != null && !"".equals(folderNm)) {
						File dir2 = new File(filesArr[i].toString());
						File[] filesArr2 = dir2.listFiles();
						for(int j = 0; j < filesArr2.length; j++) {
							HashMap<String, Object> returnMap = new HashMap<String, Object>();
							oriFileNm = filesArr2[j].getName(); // 파일명
							extNm = oriFileNm.substring(oriFileNm.lastIndexOf(".")); // 확장자
							returnMap.put("FILE_NAME", oriFileNm);
							returnMap.put("FILE_PATH", "/"+folderNm+"/");
							returnMap.put("FILE_ETC", extNm);
							returnMap.put("GALLERY_SEQ", gallerySeq);
							returnMap.put("PAGE_SEQ", j+1);
							result = dao.insertGalleryDetail(returnMap);
						}
					} else {
						log.debug("No folder");
						continue;
					}
					successCnt += result;
				} catch (Exception e) {
					e.printStackTrace();
				}
				log.error(folderNm+":: Clear");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if( successCnt > 0 ) {
			resultMap.put("result", true);
		} else {
			resultMap.put("result", false);
		}
		return resultMap;
	}
	/**
	 * 상세보기
	 */
	@Override
	public HashMap<String, Object> selectGalleryDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HashMap<String, Object> paramMap = CommonUtil.request2mapObject(request);
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		List<HashMap<String, Object>> resultList = dao.selectGalleryDetail(paramMap);
		if(resultList != null && resultList.size() > 0) {
			resultMap.put("resultList", resultList);
		}		
		return resultMap;
	}

}
