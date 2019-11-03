package com.garlickim.book.search.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account
{

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String  username;

    private String  password;

    private String  role;





    public void encodePassword(PasswordEncoder passwordEncoder)
    {
        this.password = passwordEncoder.encode(this.password);
    }

}