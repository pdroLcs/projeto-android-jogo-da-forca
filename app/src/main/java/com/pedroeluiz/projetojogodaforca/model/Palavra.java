package com.pedroeluiz.projetojogodaforca.model;

public class Palavra {

    private String palavra;
    private String categoria;

    public Palavra(String palavra, String categoria) {
        this.palavra = palavra;
        this.categoria = categoria;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Dificuldade getDificuldade() {
        int tamanho = palavra.length();
        if (tamanho <= 4) {
            return Dificuldade.FACIL;
        } else if (tamanho <= 7) {
            return Dificuldade.MEDIO;
        } else {
            return Dificuldade.DIFICIL;
        }
    }
}
