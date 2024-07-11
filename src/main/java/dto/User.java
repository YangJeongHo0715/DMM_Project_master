package dto;

import lombok.Data;

@Data
public class User {
	private int userId;
    private String userEmail;
    private String userName;
    private String userNick;
    private String userAge;
    private String gender;
    private String password;
    private String phone;
    private String zipCode;
    private String address;
    private String addressDetail;
    private String joinDate;
    private String level; 
    private String userStatus;
    private String userLoginStatus;
    private String onlineStatus;
}
