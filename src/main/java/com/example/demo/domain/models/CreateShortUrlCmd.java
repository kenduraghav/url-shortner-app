package com.example.demo.domain.models;

public record CreateShortUrlCmd(String originalUrl, Boolean isPrivate, Long createBy, Integer expirationInDays) {

}
