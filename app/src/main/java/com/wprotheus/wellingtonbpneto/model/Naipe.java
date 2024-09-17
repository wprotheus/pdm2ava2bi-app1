package com.wprotheus.wellingtonbpneto.model;

import com.wprotheus.wellingtonbpneto.R;

public enum Naipe {
    AC(R.drawable.ac, 1),
    AD(R.drawable.ad, 1),
    AH(R.drawable.ah, 1),
    AS(R.drawable.as, 1),
    KC(R.drawable.kc, 30),
    KD(R.drawable.kd, 30),
    KH(R.drawable.kh, 30),
    KS(R.drawable.ks, 30),
    QC(R.drawable.qc, 20),
    QD(R.drawable.qd, 20),
    QH(R.drawable.qh, 20),
    QS(R.drawable.qs, 20),
    JC(R.drawable.jc, 10),
    JD(R.drawable.jd, 10),
    JH(R.drawable.jh, 10),
    JS(R.drawable.js, 10),
    C2(R.drawable.c2c, 2),
    C3(R.drawable.c3c, 3),
    C4(R.drawable.c4c, 4),
    C5(R.drawable.c5c, 5),
    C6(R.drawable.c6c, 6),
    C7(R.drawable.c7c, 7),
    C8(R.drawable.c8c, 8),
    C9(R.drawable.c9c, 9),
    C0(R.drawable.c0c, 10),
    D2(R.drawable.d2d, 2),
    D3(R.drawable.d3d, 3),
    D4(R.drawable.d4d, 4),
    D5(R.drawable.d5d, 5),
    D6(R.drawable.d6d, 6),
    D7(R.drawable.d7d, 7),
    D8(R.drawable.d8d, 8),
    D9(R.drawable.d9d, 9),
    D0(R.drawable.d0d, 10),
    H2(R.drawable.h2h, 2),
    H3(R.drawable.h3h, 3),
    H4(R.drawable.h4h, 4),
    H5(R.drawable.h5h, 5),
    H6(R.drawable.h6h, 6),
    H7(R.drawable.h7h, 7),
    H8(R.drawable.h8h, 8),
    H9(R.drawable.h9h, 9),
    H0(R.drawable.h0h, 10),
    S2(R.drawable.s2s, 2),
    S3(R.drawable.s3s, 3),
    S4(R.drawable.s4s, 4),
    S5(R.drawable.s5s, 5),
    S6(R.drawable.s6s, 6),
    S7(R.drawable.s7s, 7),
    S8(R.drawable.s8s, 8),
    S9(R.drawable.s9s, 9),
    S0(R.drawable.s0s, 10);

    private final int resourceId;
    private final int valor;

    Naipe(int resourceId, int valor) {
        this.resourceId = resourceId;
        this.valor = valor;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getValor() {
        return valor;
    }
}