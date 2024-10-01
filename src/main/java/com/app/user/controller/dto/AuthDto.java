package com.app.user.controller.dto;

/**
 * authdto.
 *
 * @param email the email
 * @param password the password
 */
public record AuthDto(String email, String password) {
}