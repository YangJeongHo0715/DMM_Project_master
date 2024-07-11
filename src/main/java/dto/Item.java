package dto;

import lombok.Data;

@Data
public class Item {
    private int itemId;
    private String sellerId;
    private String buyerId;
    private String caId;
    private String title;
    private String description;
    private String price;
    private String conditionItem;
    private String listingDate;
    private String status; 
    private String file1;
    private String file2;
    private String file3;
    private String file4;
    private String file5;
    private String latitude;
    private String longitude;
    private String location;
    private int readcount;
    private int userLike;
}
