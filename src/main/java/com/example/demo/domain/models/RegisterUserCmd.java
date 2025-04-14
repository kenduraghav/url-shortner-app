package com.example.demo.domain.models;

public record RegisterUserCmd(String email, String password, String name, Role role) {

}
