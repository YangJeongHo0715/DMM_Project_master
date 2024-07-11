package dto;

import lombok.Data;

@Data
public class Transaction {
    private int transactionId;
    private String sellerId;
    private String buyerId;
    private int itemId;
    private String dealDate;
    private String transactionDate;
    private String location;
    private String status;  
}
