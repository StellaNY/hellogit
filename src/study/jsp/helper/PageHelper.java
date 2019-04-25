package study.jsp.helper;

public class PageHelper {
	/***** GET파라미터로 처리할 값 *****/
	private int page = 1; // 현재 페이지 번호

	/***** DB에서 조회한 결과 값 *****/
	private int totalCnt = 0; // 전체 글 개수 조회

	/***** 개발자가 정의해야 하는 값 *****/
	private int listCnt = 0; // 한 페이지에 보여질 글의 목록 수
	private int groupCnt = 5; // 한 그룹에 표시할 페이지 번호 개수

	/***** 연산처리가 필요한 값 *****/
	private int totalPage = 0; // 전체 페이지 수
	private int startPage = 0; // 현재 그룹의 시작 페이지
	private int endPage = 0; // 현재 그룹의 마지막 페이지
	private int prevPage = 0; // 이전 그룹의 마지막 페이지
	private int nextPage = 0; // 이전 그룹의 첫 페이지
	private int limitStart = 0; // MySQL의 Limit 시작 위치

	// ----------- 싱글톤 객체 생성 시작 ----------
	private static PageHelper current = null;

	public static PageHelper getInstance() {
		if (current == null) {
			current = new PageHelper();
		}
		return current;
	}

	public static void freeInstance() {
		current = null;
	}

	private PageHelper() {
		super();
	}
	// ----------- 싱글톤 객체 생성 끝 ----------

	public void pageProcess(int page, int totalCnt, int listCnt, int groupCnt) {
		this.page = page;
		this.totalCnt = totalCnt;
		this.listCnt = listCnt;
		this.groupCnt = groupCnt;

		// 전체 페이지 수
		totalPage = ((totalCnt - 1) / listCnt) + 1;

		// 현재 페이지에 대한 오차 조절
		if (page < 0) {
			page = 1;
		}
		if (page > totalPage) {
			page = totalPage;
		}

		// 현재 페이징 그룹의 시작 페이지 번호
		startPage = ((page - 1) / groupCnt) * groupCnt + 1;

		// 현재 페이징 그룹의 끝 페이지 번호
		endPage = (((startPage - 1) + groupCnt) / groupCnt) * groupCnt;

		// 끝 페이지 번호가 전체 페이지수를 초과하면 오차범위 조절
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		// 이전 그룹의 마지막 페이지
		if (startPage > groupCnt) {
			prevPage = startPage - 1;
		} else {
			prevPage = 0;
		}

		// 다음 그룹의 첫 페이지
		if (endPage < totalPage) {
			nextPage = endPage + 1;
		} else {
			nextPage = 0;
		}

		// 검색 범위의 시작 위치
		limitStart = (page - 1) * listCnt;
	}

	/***** 멤버변수를 JSP에게 전달하기 위한 getter/setter *****/

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	public int getListCnt() {
		return listCnt;
	}

	public void setListCnt(int listCnt) {
		this.listCnt = listCnt;
	}

	public int getGroupCnt() {
		return groupCnt;
	}

	public void setGroupCnt(int groupCnt) {
		this.groupCnt = groupCnt;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(int prevPage) {
		this.prevPage = prevPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	@Override
	public String toString() {
		return "PageHelper [page=" + page + ", totalCnt=" + totalCnt + ", listCnt=" + listCnt + ", groupCnt=" + groupCnt
				+ ", totalPage=" + totalPage + ", startPage=" + startPage + ", endPage=" + endPage + ", prevPage="
				+ prevPage + ", nextPage=" + nextPage + ", limitStart=" + limitStart + "]";
	}

}
