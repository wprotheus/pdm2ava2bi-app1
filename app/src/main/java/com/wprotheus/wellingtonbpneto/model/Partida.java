package com.wprotheus.wellingtonbpneto.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Partida implements Serializable {
    private int id;
    private String nome;
    private String palpite;
    private String resultado;
}