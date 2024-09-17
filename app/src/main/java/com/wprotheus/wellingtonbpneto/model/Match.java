package com.wprotheus.wellingtonbpneto.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class Match implements Serializable {
    public int[] cartasEscolhidas(int quantidade) {
        return new Random().ints(0, 51)
                .distinct()
                .limit(quantidade)
                .sorted()
                .toArray();
    }
}