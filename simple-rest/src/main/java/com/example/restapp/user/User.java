package com.example.restapp.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Data;

/**
 * Created by Jakub Krhovják on 11/21/22.
 */

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="user_id_seq",sequenceName="user_id_seq", allocationSize=1)
    public Long id;

    public String username;

    private String password;
}
