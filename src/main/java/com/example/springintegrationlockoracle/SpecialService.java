package com.example.springintegrationlockoracle;

import org.springframework.stereotype.Service;

@Service
public class SpecialService {
    public void special(String foo, String bar) {
        System.out.println("SpecialService.special() " + foo + " " + bar);
    }
}
