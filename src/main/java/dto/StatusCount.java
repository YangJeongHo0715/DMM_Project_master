package dto;

import lombok.Data;

@Data
public class StatusCount {
	private int cancel_count;
	private int booking_count;
	private int complete_count;
}
