package com.wprotheus.wellingtonbpneto.viewmodel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.wprotheus.wellingtonbpneto.model.Baralho;
import com.wprotheus.wellingtonbpneto.model.Naipe;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TratarDados extends Fragment {
    private List<String> listCartas;
    private Baralho deckCards = null;

    private static List<String> getValoresCarta(List<String> listCartas, int x) {
        return IntStream.range(0, listCartas.size())
                .filter(c -> c % 4 == x)
                .mapToObj(listCartas::get)
                .collect(Collectors.toList());
    }

    public static @NonNull separarValoresBaralho getSepararValoresBaralho(List<String> listCartas) {
        List<String> listDeckCode = getValoresCarta(listCartas, 0);
        List<String> listDeckImage = getValoresCarta(listCartas, 1);
        List<String> listDeckValue = getValoresCarta(listCartas, 2);
        List<String> listDeckSuit = getValoresCarta(listCartas, 3);
        return new separarValoresBaralho(listDeckCode, listDeckImage, listDeckValue, listDeckSuit);
    }

    public static @NonNull String getCodigoCarta(String codigo) {
        return Character.isDigit(codigo.charAt(0)) ? new StringBuilder(codigo).reverse().toString() : codigo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public List<String> prepararDados(Baralho baralho) {
        listCartas = new ArrayList<>();
        deckCards = new Baralho();
        deckCards.setSuccess(baralho.getSuccess());
        deckCards.setDeck_id(baralho.getDeck_id());
        deckCards.setCards(baralho.getCards());
        deckCards.setRemaining(baralho.getRemaining());
        return prepararCartas(baralho);
    }

    private List<String> prepararCartas(Baralho baralho) {
        for (int i = 0; i < baralho.getCards().size(); i++) {
            listCartas.add(baralho.getCards().get(i).getCode());
            listCartas.add(baralho.getCards().get(i).getImage());
            listCartas.add(baralho.getCards().get(i).getValue());
            listCartas.add(baralho.getCards().get(i).getSuit());
        }
        return listCartas;
    }

    public List<String> cartasSelecionadas(List<String> listCartas, int[] qtdEscolhida) {
        List<String> listCardsRandon = new ArrayList<>();
        int somaValoresCartas = 0;
        separarValoresBaralho result = getSepararValoresBaralho(listCartas);

        for (int i = 0; i < qtdEscolhida.length; i++) {
            listCardsRandon.add(result.listDeckCode.get(qtdEscolhida[i]));
            listCardsRandon.add(result.listDeckImage.get(qtdEscolhida[i]));
            listCardsRandon.add(result.listDeckValue.get(qtdEscolhida[i]));
            listCardsRandon.add(result.listDeckSuit.get(qtdEscolhida[i]));
            somaValoresCartas += somandoValores(result.listDeckCode.get(qtdEscolhida[i]));
        }
        listCardsRandon.add(String.valueOf(somaValoresCartas));
        return listCardsRandon;
    }

    private int somandoValores(String valorCarta) {
        return Naipe.valueOf(getCodigoCarta(valorCarta)).getValor();
    }

    public List<String> listaAdapter(List<String> listBaralho) {
        return IntStream.range(0, listBaralho.size())
                .filter(i -> (i % 4) != 1) // Ignorando a coluna de imagem no adapter
                .mapToObj(listBaralho::get)
                .collect(Collectors.toList());
    }

    public static class separarValoresBaralho {
        public final List<String> listDeckCode;
        public final List<String> listDeckImage;
        public final List<String> listDeckValue;
        public final List<String> listDeckSuit;

        public separarValoresBaralho(List<String> listDeckCode, List<String> listDeckImage, List<String> listDeckValue, List<String> listDeckSuit) {
            this.listDeckCode = listDeckCode;
            this.listDeckImage = listDeckImage;
            this.listDeckValue = listDeckValue;
            this.listDeckSuit = listDeckSuit;
        }
    }
}