package com.wprotheus.wellingtonbpneto.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Cards extends Baralho implements Serializable {
    private String code;
    private String image;
    private Imagens images;
    private String value;
    private String suit;
}