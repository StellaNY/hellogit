package study.jsp.mysite.service;

import java.util.List;

import study.jsp.mysite.model.BbsFile;

public interface BbsFileService {
	
	/**
	 * 파일 저장
	 * @param file
	 * @throws Exception
	 */
	public void insertFile(BbsFile file) throws Exception;

	/**
	 * 게시물의 파일 목록을 조회한다.
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public List<BbsFile> selectFileList(BbsFile file) throws Exception;
	
	/**
	 * 게시글에 속한 첨부파일들을 삭제한다.
	 * @param document
	 * @throws Exception
	 */
	public void deleteFileAll(BbsFile bbsFile) throws Exception;
	/**
	 * 하나의 단일 파일에 대한 정보를 조회한다.
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public BbsFile selectFile(BbsFile file) throws Exception;
	
	/**
	 * 게시글에 속한 하나의 첨부파일을 삭제한다.
	 * @param document
	 * @throws Exception
	 */
	public void deleteFile(BbsFile bbsFile) throws Exception;
}
