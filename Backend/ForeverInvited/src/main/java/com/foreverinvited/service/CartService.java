package com.foreverinvited.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final List<com.foreverinvited.model.Service> cart = new ArrayList<>(); 
}
