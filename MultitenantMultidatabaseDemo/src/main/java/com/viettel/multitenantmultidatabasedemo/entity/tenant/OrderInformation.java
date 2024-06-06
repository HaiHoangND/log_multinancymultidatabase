package com.viettel.multitenantmultidatabasedemo.entity.tenant;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "order_information")
public class OrderInformation {
    @Id
    private String id;
    private String customerName;
    private List<Product> products;
}
