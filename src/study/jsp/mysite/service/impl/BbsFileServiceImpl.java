package study.jsp.mysite.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.Logger;

import study.jsp.mysite.model.BbsFile;
import study.jsp.mysite.service.BbsFileService;

public class BbsFileServiceImpl implements BbsFileService {

	Logger log;
	SqlSession sqlSession;

	public BbsFileServiceImpl(Logger log, SqlSession sqlSession) {
		super();
		this.log = log;
		this.sqlSession = sqlSession;
	}

	@Override
	public void insertFile(BbsFile file) throws Exception {
		try {
			int result = sqlSession.insert("BbsFileMapper.insertFile", file);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			throw new Exception("저장된 파일정보가 없습니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("파일 정보 등록에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}

	}

	@Override
	public List<BbsFile> selectFileList(BbsFile file) throws Exception {
		List<BbsFile> result = null;
		try {
			result = sqlSession.selectList("BbsFileMapper.selectFileList", file);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("첨부파일 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public void deleteFileAll(BbsFile bbsFile) throws Exception {
		try {
			int result = sqlSession.delete("BbsDocMapper.deleteFileAll", bbsFile);
			// 첨부파일이 없는 경우도 있으므로, 결과가 0인것은 예외를 발생 x
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("첨부파일 정보 삭제에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}

	}

	@Override
	public BbsFile selectFile(BbsFile file) throws Exception {
		BbsFile result = null;
		try {
			result = sqlSession.selectOne("BbsFileMapper.selectFile", file);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("존재하지 않는 파일에 대한 요청입니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("파일정보 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public void deleteFile(BbsFile bbsFile) throws Exception {
		try {
			int result = sqlSession.delete("BbsDocMapper.deleteFile", bbsFile);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			throw new Exception("삭제된 파일 정보가 없습니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("첨부파일 정보 삭제에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}
	}

}
