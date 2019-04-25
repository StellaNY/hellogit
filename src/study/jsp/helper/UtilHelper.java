package study.jsp.helper;

import java.util.Random;

/**
 * 기본적인 공통 기능들을 묶어 놓은 클래스
 */
public class UtilHelper {
	
	static UtilHelper utilHelper = null;
	
	public static UtilHelper getInstance() {
		if (utilHelper == null) {
			utilHelper = new UtilHelper();
		}
		return utilHelper;
	}
	
	public static void freeInstance() {
		utilHelper = null;
	}
	
	private UtilHelper() {	}
	
	/**
	 *  범위를 갖는 랜덤값을 생성하여 리턴하는 메서드
	 * @param min		- 범위 안에서의 최소값
	 * @param max		- 범위 안에서의 최대값
	 * @return	min~max 안에서의 랜덤값
	 */
	public int random(int min, int max) {
		int num = (int) ((Math.random() * (max - min +1)) + min);
		return num;
	}

	/**
	 *  특수문자를 공백으로 처리
	 * @param str     - 문자열 파라미터
	 * @return String - 특수문자를 공백으로 처리하여 출력
	 */
	public String StringReplace(String str) {
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
		str = str.replaceAll(match, " ");
		
		return str;
	}
	/**
	 *  원하는 길이만큼 랜덤 문자 출력
	 * @param len	- 원하는 랜덤 길이
	 * @return	String - len 길이 만큼 랜덤 문자 출력
	 */
	 public String getRandomPassword(int len) {
	        char[] charaters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
	                'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
	                's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	        StringBuffer sb = new StringBuffer();
	        Random rn = new Random();
	        for (int i = 0; i < len; i++) {
	            sb.append(charaters[rn.nextInt(charaters.length)]);
	        }
	        return sb.toString();
	    }
}
