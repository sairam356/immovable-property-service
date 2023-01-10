package com.immovable.propertyservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cart_tbl")
public class Cart {

    @Id
    private String id;
    private String customerId;
    @DBRef
    private List<CartItems> cartItems;
    private String status;

    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", cartItems=" + cartItems +
                ", status='" + status + '\'' +
                '}';
    }
}
