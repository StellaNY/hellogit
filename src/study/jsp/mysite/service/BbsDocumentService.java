package study.jsp.mysite.service;

import java.util.List;

import study.jsp.mysite.model.BbsDocument;

/**
 * 
 * @author StellaNY
 * @since 2019. 03. 29.
 */
public interface BbsDocumentService {
	
	/**
	 * 게시물을 저장한다.
	 * @param document - 게시물 데이터
	 * @throws Exception
	 */
	public void insertDocument(BbsDocument document) throws Exception;
	
	/**
	 * 게시판 목록을 읽어들인다.
	 * @param document
	 * @return BbsDocument - 읽어들인 게시물 내용
	 * @throws Exception
	 */
	public List<BbsDocument> selectDocumentList(BbsDocument document) throws Exception;
	
	/**
	 * 하나의 게시물을 읽어들인다.
	 * @param document
	 * @return BbsDocument - 읽어들인 게시물 내용
	 * @throws Exception
	 */
	public BbsDocument selectDocument(BbsDocument document) throws Exception;
	
	/**
	 * 현재글을 기준으로 이전글을 읽어들인다.
	 * @param document
	 * @return BbsDocument - 읽어들인 게시물 내용
	 * @throws Exception
	 */
	public BbsDocument selectPrevDocument(BbsDocument document) throws Exception;
	
	/**
	 * 현재글을 기준으로 다음글을 읽어들인다.
	 * @param document
	 * @return BbsDocument - 읽어들인 게시물 내용
	 * @throws Exception
	 */
	public BbsDocument selectNextDocument(BbsDocument document) throws Exception;
	
	/**
	 * 조회수를 1 증가 시킨다.
	 * @param document
	 * @throws Exception
	 */
	public void updateDocumentHit(BbsDocument document) throws Exception;

	/**
	 * 페이징을 위한 전체 카운트수 조회
	 * @param document
	 * @return
	 * @throws Exception
	 */
	public int selectDocumentCnt(BbsDocument document) throws Exception;
	
	/**
	 * 자신의 게시물인지 검사한다.
	 * @param document
	 * @return int
	 * @throws Exception
	 */
	public int selectDocumentCntByMemberId(BbsDocument document) throws Exception;
	
	/**
	 * 비밀번호를 검사한다.
	 * @param document
	 * @return int
	 * @throws Exception
	 */
	public int selectDocumentCntByPw(BbsDocument document) throws Exception;
	
	/**
	 * 게시글을 삭제한다.
	 * @param document
	 * @throws Exception
	 */
	public void deleteDocument(BbsDocument document) throws Exception;
	
	/**
	 * 게시글을 수정한다.
	 * @param document
	 * @throws Exception
	 */
	public void updateDocument(BbsDocument document) throws Exception;
}
