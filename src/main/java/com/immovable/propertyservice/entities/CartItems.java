package com.immovable.propertyservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cart_items_tbl")
public class CartItems {

    @Id
    private String id;
    private String propertyId;
    private BigDecimal price;
    private String status;

    @Override
    public String toString() {
        return "CartItems{" +
                "id='" + id + '\'' +
                ", propertyId='" + propertyId + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}
