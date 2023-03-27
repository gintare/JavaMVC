package com.example.javamvc.models;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(nullable = false, unique = true)
    public String username;

    public String password;
}