package com.remon.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("FileDao")
public class ViewDao {
	
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	@Autowired
	@Resource(name = "readSqlSessionTemplate")
	private SqlSessionTemplate sqlSessionRead;

	public List<HashMap<String, Object>> selectGalleryList(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("ViewSQL.selectGalleryList", paramMap);
	}

	public int selectGallerySeq() {
		return sqlSession.selectOne("ViewSQL.selectGallerySeq");
	}
	public int insertGallery(HashMap<String, Object> paramMap) {
		return sqlSession.insert("ViewSQL.insertGallery", paramMap);
	}
	
	public int insertGalleryDetail(HashMap<String, Object> paramMap) {
		return sqlSession.insert("ViewSQL.insertGalleryDetail", paramMap);
	}

	public List<HashMap<String, Object>> selectGalleryDetail(HashMap<String, Object> paramMap) {
		return sqlSession.selectList("ViewSQL.selectGalleryDetail", paramMap);
	}

	public int selectGalleryCnt(HashMap<String, Object> paramMap) {
		return sqlSession.selectOne("ViewSQL.selectGalleryCnt", paramMap);
	}

	public List<HashMap<String, Object>> selectGalleryAuthList() {
		return sqlSession.selectList("ViewSQL.selectGalleryAuthList");
	}

	public void deleteGalleryDetail(HashMap<String, Object> authMap) {
		sqlSession.delete("ViewSQL.deleteGalleryDetail", authMap);
	}
	
	public void deleteGallery(HashMap<String, Object> authMap) {
		sqlSession.delete("ViewSQL.deleteGallery", authMap);
	}

}
