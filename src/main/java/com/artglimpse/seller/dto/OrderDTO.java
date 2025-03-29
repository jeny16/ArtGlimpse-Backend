package com.artglimpse.seller.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class OrderDTO {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date orderDate;
}
