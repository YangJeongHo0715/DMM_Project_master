package dto;

import lombok.Data;

@Data
public class ChatRoom {
    private int roomId;
    private int itemId;
    private String buyer;
    private String seller;
    private Item item; // 아이템 정보 추가
    private int readCount; //읽음(DB저장 X)
}
