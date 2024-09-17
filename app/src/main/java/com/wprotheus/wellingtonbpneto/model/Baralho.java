package com.wprotheus.wellingtonbpneto.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Baralho implements Serializable {
    private Boolean success;
    private String deck_id;
    private List<Cards> cards;
    private String remaining;
}