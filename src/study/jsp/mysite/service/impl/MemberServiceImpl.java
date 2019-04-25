package study.jsp.mysite.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.Logger;

import study.jsp.mysite.model.Member;
import study.jsp.mysite.service.MemberService;

public class MemberServiceImpl implements MemberService {

	Logger log;

	SqlSession sqlSession;

	public MemberServiceImpl(Logger log, SqlSession sqlSession) {
		super();
		this.log = log;
		this.sqlSession = sqlSession;
	}

	@Override
	public void selectChkMemberId(Member member) throws Exception {
		try {
			int result = sqlSession.selectOne("MemberMapper.selectChkMemberId", member);
			if (result > 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("이미 사용중인 아이디입니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("아이디 중복검사에 실패했습니다.");
		}
	}

	@Override
	public void selectChkMemberEmail(Member member) throws Exception {
		try {
			int result = sqlSession.selectOne("MemberMapper.selectChkMemberEmail", member);
			if (result > 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("이미 사용중인 이메일입니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("이메일 중복검사에 실패했습니다.");
		}
	}

	@Override
	public void insertJoinMember(Member member) throws Exception {
		selectChkMemberId(member);
		selectChkMemberEmail(member);
		try {
			int result = sqlSession.insert("MemberMapper.insertJoinMember", member);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			throw new Exception("저장된 회원정보가 없습니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("회원가입에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}
	}

	@Override
	public Member selectLoginInfo(Member member) throws Exception {
		Member result = null;
		try {

			result = sqlSession.selectOne("MemberMapper.selectLoginInfo", member);

			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("아이디나 비밀번호가 잘못되었습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("로그인에 실패했습니다.");
		}

		return result;
	}

	@Override
	public void updateMemberPwByEmail(Member member) throws Exception {
		try {
			int result = sqlSession.update("MemberMapper.updateMemberPwByEmail", member);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			throw new Exception("가입된 이메일이 아닙니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("비밀번호 변경에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}

	}

	@Override
	public void selectMemberPasswordCnt(Member member) throws Exception {
		try {
			int result = sqlSession.selectOne("MemberMapper.selectMemberPasswordCnt", member);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("잘못된 비밀번호 입니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("비밀번호 검사에 실패했습니다.");
		}
	}

	@Override
	public void deleteMember(Member member) throws Exception {
		try {
			int result = sqlSession.delete("MemberMapper.deleteMember", member);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			throw new Exception("이미 탈퇴된 회원입니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("회원탈퇴에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}
	}

	@Override
	public void updateMember(Member member) throws Exception {
		try {
			int result = sqlSession.update("MemberMapper.updateMember", member);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			sqlSession.rollback();
			throw new Exception("변경된 회원정보가 없습니다.");
		} catch (Exception e) {
			sqlSession.rollback();
			log.error(e.getLocalizedMessage());
			throw new Exception("회원정보 수정에 실패했습니다.");
		} finally {
			sqlSession.commit();
		}

	}

	@Override
	public Member selectMember(Member member) throws Exception {
		Member result = null;
		try {

			result = sqlSession.selectOne("MemberMapper.selectMember", member);

			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 정보가 없습니다.");
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new Exception("회원정보 조회에 실패했습니다.");
		}

		return result;
	}
}
