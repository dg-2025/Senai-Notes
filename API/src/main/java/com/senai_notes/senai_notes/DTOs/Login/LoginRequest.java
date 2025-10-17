package com.senai_notes.senai_notes.DTOs.Login;

import lombok.Data;

@Data
public class LoginRequest  {
    public String email;
    public String senha;
}