package study.jsp.mysite.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.Logger;

import study.jsp.mysite.model.BbsDocument;
import study.jsp.mysite.service.BbsDocumentService;

public class BbsDocumentServiceImpl implements BbsDocumentService {

	Logger log;
	SqlSession sqlSession;

	public BbsDocumentServiceImpl(Logger log, SqlSession sqlSession) {
		super();
		this.log = log;
		this.sqlSession = sqlSession;
	}

	@Override
	public void insertDocument(BbsDocument document) throws Exception {
		try {
			int result = sqlSession.insert("BbsDocMapper.insertDocument", document);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			throw new Exception("저장된 게시물이 없습니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("게시물 정보 등록에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}
	}

	@Override
	public BbsDocument selectDocument(BbsDocument document) throws Exception {
		BbsDocument result = null;
		try {
			result = sqlSession.selectOne("BbsDocMapper.selectDocument", document);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 게시물이 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("게시물 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public BbsDocument selectPrevDocument(BbsDocument document) throws Exception {
		BbsDocument result = null;
		try {
			result = sqlSession.selectOne("BbsDocMapper.selectPrevDocument", document);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("이전글 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public BbsDocument selectNextDocument(BbsDocument document) throws Exception {
		BbsDocument result = null;
		try {
			result = sqlSession.selectOne("BbsDocMapper.selectNextDocument", document);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("이전글 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public void updateDocumentHit(BbsDocument document) throws Exception {
		try {
			int result = sqlSession.update("BbsDocMapper.updateDocumentHit", document);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			throw new Exception("존재하지 않는 게시물에 대한 요청입니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("조회수 갱신에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}

	}

	@Override
	public List<BbsDocument> selectDocumentList(BbsDocument document) throws Exception {

		List<BbsDocument> result = null;
		try {
			result = sqlSession.selectList("BbsDocMapper.selectDocumentList", document);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 글 목록이 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("글 목록 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public int selectDocumentCnt(BbsDocument document) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BbsDocMapper.selectDocumentCnt", document);
			
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("게시물 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public int selectDocumentCntByMemberId(BbsDocument document) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BbsDocMapper.selectDocumentCntByMemberId", document);
			
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("게시물 수 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public int selectDocumentCntByPw(BbsDocument document) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BbsDocMapper.selectDocumentCntByPw", document);
			if(result < 1) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("비밀번호를 확인하세요.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("게시물 수 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public void deleteDocument(BbsDocument document) throws Exception {
		try {
			int result = sqlSession.delete("BbsDocMapper.deleteDocument", document);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			throw new Exception("존재하지 않는 게시물에 대한 요청입니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("게시물 삭제에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}
	}

	@Override
	public void updateDocument(BbsDocument document) throws Exception {
		try {
			int result = sqlSession.update("BbsDocMapper.updateDocument", document);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			throw new Exception("존재하지 않는 게시물에 대한 요청입니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("게시물 수정에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}

	}

}
