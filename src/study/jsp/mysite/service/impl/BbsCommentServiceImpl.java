package study.jsp.mysite.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.Logger;

import study.jsp.mysite.model.BbsComment;
import study.jsp.mysite.service.BbsCommentService;

/**
 * 
 * <pre>
 * &#64;Project : mysite
 * &#64;PackageNm : study.jsp.mysite.service.impl
 * </pre>
 * 
 * @FileName : BbsCommentServiceImpl.java
 * @author : "StellaNY (stella10137@gmail.com)"
 * @Description : 덧글 관련 기능을 제공하기 위한 Service 기능 구현
 * @Date : 2019-04-03
 *
 */
public class BbsCommentServiceImpl implements BbsCommentService {

	Logger log;
	SqlSession sqlSession;

	public BbsCommentServiceImpl(Logger log, SqlSession sqlSession) {
		super();
		this.log = log;
		this.sqlSession = sqlSession;
	}

	@Override
	public void insertComment(BbsComment bbsComment) throws Exception {
		try {
			int result = sqlSession.insert("BbsComMapper.insertComment", bbsComment);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			throw new Exception("저장된 덧글이 없습니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("덧글 정보 등록에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}
	}

	@Override
	public BbsComment selectComment(BbsComment bbsComment) throws Exception {
		BbsComment result = null;
		try {
			result = sqlSession.selectOne("BbsComMapper.selectComment", bbsComment);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 덧글이 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("덧글 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public List<BbsComment> selectCommentList(BbsComment bbsComment) throws Exception {
		List<BbsComment> result = null;
		try {
			result = sqlSession.selectList("BbsComMapper.selectCommentList", bbsComment);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 덧글이 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("덧글 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public int selectCommentCnt(BbsComment bbsComment) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BbsComMapper.selectCommentCnt", bbsComment);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 덧글이 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("덧글 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public int selectCommentCntByMemberId(BbsComment bbsComment) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BbsComMapper.selectCommentCntByMemberId", bbsComment);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("덧글 수 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public int selectCommentCntByPw(BbsComment bbsComment) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BbsComMapper.selectCommentCntByPw", bbsComment);
			if (result < 1) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("덧글 비밀번호를 확인하세요.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("덧글수 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public int deleteComment(BbsComment bbsComment) throws Exception {
		int result = 0;
		try {
			result = sqlSession.delete("BbsComMapper.deleteComment", bbsComment);
			if (result == 0 ) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			throw new Exception("존재하지 않는 덧글에 대한 요청입니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("덧글 삭제에 실패했습니다.");
		}finally {
			sqlSession.commit();
		}
		return result;
	}

	@Override
	public void updateComment(BbsComment bbsComment) throws Exception {
		try {
			int result = sqlSession.update("BbsComMapper.updateComment", bbsComment);
			if (result == 0 ) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			throw new Exception("존재하지 않는 덧글에 대한 요청입니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("덧글 수정에 실패했습니다.");
		}finally {
			sqlSession.commit();
		}
	}

}
