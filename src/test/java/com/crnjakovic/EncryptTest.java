package com.crnjakovic;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by lukacrnjakovic on 4/17/18.
 */
public class EncryptTest {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("12345678"));
    }
}
