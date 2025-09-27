package com.senai_notes.senai_notes.dto;

import lombok.Data;

@Data
public class LoginRequest {
    public String email;
    public String senha;
}