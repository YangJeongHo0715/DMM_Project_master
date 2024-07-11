package dto;

import lombok.Data;

@Data
public class QnA {
	private int num; //게시물 고유번호 
	private String subject; //게시물의 제목 
	private String regdate; //게시물의 작성일시 
	private int ref; // 답글의 그룹을 식별하기 위한 참조 번호 
	private int restep; //답글의 순서를 나타내는 단계 
	private int relevel; //답글의 깊이를 나타내는 수준 
	private String content; //게시물의 내용
	private int status; //게시물의 상태를 나타내는 필드(게시, 비게시)
	private String writer; //게시물을 작성한 사용자의 이름 
	private String file; //첨부 파일
}
