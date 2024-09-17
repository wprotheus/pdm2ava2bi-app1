package com.wprotheus.wellingtonbpneto.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Imagens extends Cards implements Serializable {
    private String svg;
    private String png;
}