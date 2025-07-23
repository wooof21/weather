package com.weather.model;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table("auth_users")
@Data
@Builder
public class AuthUsers {

    @Id
    private Integer id;

    private String username;
    private String password;
}
