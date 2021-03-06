package com.pj.cherrypick.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.pj.cherrypick.domain.BookmarkVO;

@Repository("com.pj.cherrypick.mapper.BookmarkMapper")
@Mapper
public interface BookmarkMapper {

	
	// 내(username)가 이 카페(cno)를 북마크했는지 체크
	BookmarkVO checkCafeBmk(Map<String, Object> m) throws Exception;
	
	// 내(username)가 전체 카페(cafe/all) 북마크했는지 체크
	BookmarkVO checkCafeBmkAll(BookmarkVO vo) throws Exception;
	
	
	// 내(username)가 이 리스트(lino)를 북마크 했는지 체크
	BookmarkVO checkListBmk(Map<String, Object> m) throws Exception;	
	
	
	// 북마크 카페 추가
	public int addBmkc(String username, int cno) throws Exception;
	
	// 북마크 카페 삭제
	public int delBmkc(String username, int cno) throws Exception;
	
	// 북마크 리스트 추가
	public int addBmkli(String username, int lino) throws Exception;
	
	// 북마크 리스트 삭제
	public int delBmkli(String username, int lino) throws Exception;
}
