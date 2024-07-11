package dto;

import lombok.Data;

@Data
public class Wishlist {
    private int sessionId;
    private int userId;
    private int itemId;
    private String price;
    private String listingdate;
    private String status;
    private String sellerId;
    private String file;
    private String title;
}
