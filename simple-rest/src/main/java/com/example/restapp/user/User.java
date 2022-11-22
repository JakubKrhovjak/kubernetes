package com.example.restapp.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by Jakub Krhovják on 11/21/22.
 */

@Data
@Accessors(chain = true)
@Entity
@Table(schema="public", name= "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name="user_id_seq",sequenceName="user_id_seq", allocationSize=1)
    public Integer id;

    public String username;

    private String password;
}
