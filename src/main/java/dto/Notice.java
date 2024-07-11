package dto;

import lombok.Data;

@Data
public class Notice {
	private int uid;
	private String subject;
	private String content;
	private String signdate;
	private int ref;
	private String file;
	private String writer;
}
