package com.viettel.multitenantmultidatabasedemo.entity.tenant;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Override
    public String toString() {
        return String.format("CustomerEntity [id=%s, fullName=%s, email=%s, address=%s]",
                id, fullName, email, address);
    }
}
