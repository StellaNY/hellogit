package study.jsp.mysite.service;

import java.util.List;

import study.jsp.mysite.model.BbsComment;
/**
 * 
 * <pre>
 * @Project : mysite
 * @PackageNm : study.jsp.mysite.service
 * </pre>
 * 
 * @FileName : BbsCommentService.java
 * @author : "StellaNY (stella10137@gmail.com)"
 * @Description : 덧글 관련 기능을 제공하기 위한 Service  계층
 * @Date : 2019-04-03
 *
 */
public interface BbsCommentService {
	
	/**
	 * 
	 * <pre>
	 * 메소드 정의 : 덧글을 저장한다.
	 * </pre>
	 * 
	 * @author : "StellaNY (stella10137@gmail.com)"
	 * @Date 2019-04-03
	 * 
	 * @Method Name : insertComment
	 * @param bbsComment - 덧글 데이터
	 * @throws Exception
	 */
	public void insertComment(BbsComment bbsComment) throws Exception;
	
	/**
	 * 
	 * <pre>
	 * 메소드 정의 : 덧글 하나를 읽어온다.
	 * </pre>
	 * 
	 * @author : "StellaNY (stella10137@gmail.com)"
	 * @Date 2019-04-03
	 * 
	 * @Method Name : selectComment
	 * @param bbsComment : 읽어들일 덧글 일련번호 
	 * @return BbsComment : 읽어들인 덧글 내용
	 * @throws Exception
	 */
	public BbsComment selectComment(BbsComment bbsComment) throws Exception;

	/**
	 * 
	 * <pre>
	 * 메소드 정의 : 한 게시글의 덧글을 리스트로 갖고 온다.
	 * </pre>
	 * 
	 * @author : "StellaNY (stella10137@gmail.com)"
	 * @Date 2019-04-03
	 * 
	 * @Method Name : selectCommentList
	 * @param bbsComment
	 * @return List<BbsComment> : 덧글 리스트를 불러온다.
	 * @throws Exception
	 */
	public List<BbsComment> selectCommentList(BbsComment bbsComment) throws Exception;
	
	/**
	 * 
	 * <pre>
	 * 메소드 정의 : 덧글을 10개씩 조회하기 위한 페이징 CNT
	 * </pre>
	 * 
	 * @author : "StellaNY (stella10137@gmail.com)"
	 * @Date 2019-04-03
	 * 
	 * @Method Name : selectCommentCnt
	 * @param bbsComment
	 * @return int : 덧글 갯수
	 * @throws Exception
	 */
	public int selectCommentCnt(BbsComment bbsComment) throws Exception;
	
	/**
	 * 
	 * <pre>
	 * 메소드 정의 : 덧글 삭제를 위한 자신 덧글 여부 검사
	 * </pre>
	 * 
	 * @author : "StellaNY (stella10137@gmail.com)"
	 * @Date 2019-04-04
	 * 
	 * @Method Name : selectCommentCntByMemberId
	 * @param bbsComment
	 * @return
	 * @throws Exception
	 */
	public int selectCommentCntByMemberId(BbsComment bbsComment) throws Exception;
	
	/**
	 * 
	 * <pre>
	 * 메소드 정의 : 덧글 삭제를 위한 덧글 비밀번호 검사
	 * </pre>
	 * 
	 * @author : "StellaNY (stella10137@gmail.com)"
	 * @Date 2019-04-04
	 * 
	 * @Method Name : selectCommentCntByPw
	 * @param bbsComment
	 * @return
	 * @throws Exception
	 */
	public int selectCommentCntByPw(BbsComment bbsComment) throws Exception;
	/**
	 * 
	 * <pre>
	 * 메소드 정의 : 자신이 작성한 덧글을 삭제한다.
	 * </pre>
	 * 
	 * @author : "StellaNY (stella10137@gmail.com)"
	 * @Date 2019-04-04
	 * 
	 * @Method Name : deleteComment
	 * @param bbsComment
	 * @return
	 * @throws Exception
	 */
	public int deleteComment(BbsComment bbsComment) throws Exception;
	
	/**
	 * 
	 * <pre>
	 * 메소드 정의 : 자신이 작성한 덧글을 수정한다.
	 * </pre>
	 * 
	 * @author : "StellaNY (stella10137@gmail.com)"
	 * @Date 2019-04-04
	 * 
	 * @Method Name : updateComment
	 * @param bbsComment - 덧글 데이터
	 * @throws Exception
	 */
	public void updateComment(BbsComment bbsComment) throws Exception;
}
