package com.example.webflux.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by Jakub Krhovják on 11/21/22.
 */

@Data
@Accessors(chain = true)

@Table(schema="public", name= "user")
public class User {//implements Persistable<User> {

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
//    @SequenceGenerator(name="user_id_seq",sequenceName="user_id_seq", allocationSize=1)
    public Integer id;

    public String username;

    private String password;

//    @Override
//    @Transient
//    public boolean isNew() {
//        return id == null;
//    }
}
