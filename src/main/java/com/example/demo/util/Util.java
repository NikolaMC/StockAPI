package com.example.demo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class Util {

    public String generateHashedProductId(String unique) {
        try {
            MessageDigest hashFunc = MessageDigest.getInstance("SHA3-256");
            byte[] hashProductId = hashFunc.digest(unique.getBytes());
            return hashProductId.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "Failed";
        }
    }
}