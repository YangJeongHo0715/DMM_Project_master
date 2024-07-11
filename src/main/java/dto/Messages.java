package dto;

import lombok.Data;

@Data
public class Messages {
	private int itemId;
	private int roomId;
	private String message;
	private String sender;
	private String receiver;
	private String time;
	private String isRead;
}
