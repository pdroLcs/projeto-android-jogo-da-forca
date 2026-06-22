package com.pedroeluiz.projetojogodaforca.repository;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pedroeluiz.projetojogodaforca.model.Palavra;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PalavraRepository {

    public void salvarPalavras(Context context, List<Palavra> palavras) {
        Gson gson = new Gson();
        String json = gson.toJson(palavras);

        try (FileOutputStream fos = context.openFileOutput("palavras.json", Context.MODE_PRIVATE)) {
            fos.write(json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean adicionarPalavra(Context context, Palavra palavra) {
        List<Palavra> palavras = carregarPalavras(context);
        boolean existe = palavras.stream().anyMatch(p -> p.getPalavra().equalsIgnoreCase(palavra.getPalavra().trim()));
        if (existe) {
            return false;
        }
        palavra.setPalavra(palavra.getPalavra().toUpperCase().trim());
        palavras.add(palavra);
        salvarPalavras(context, palavras);
        return true;
    }

    public List<Palavra> carregarPalavras(Context context) {
        try (FileInputStream fis = context.openFileInput("palavras.json")) {
            InputStreamReader reader = new InputStreamReader(fis);
            Type type = new TypeToken<List<Palavra>>(){}.getType();
            List<Palavra> palavras = new Gson().fromJson(reader, type);
            return (palavras != null) ? palavras : new ArrayList<>();
        }  catch (FileNotFoundException e) {
            return carregarPalavrasIniciais(context);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Palavra> carregarPalavrasIniciais(Context context) {
        List<Palavra> palavras = new ArrayList<>();

        palavras.add(new Palavra("JAVA", "PROGRAMAÇÃO"));

        palavras.add(new Palavra("ANDROID", "TECNOLOGIA"));

        palavras.add(new Palavra("KOTLIN", "PROGRAMAÇÃO"));

        palavras.add(new Palavra("BANCO", "TECNOLOGIA"));

        palavras.add(new Palavra("COMPUTADOR", "INFORMÁTICA"));

        palavras.add(new Palavra("TECLADO", "INFORMÁTICA"));

        palavras.add(new Palavra("ALGORITMO", "PROGRAMAÇÃO"));

        palavras.add(new Palavra("MOUSE", "INFORMÁTICA"));

        palavras.add(new Palavra("CELULAR", "TECNOLOGIA"));

        palavras.add(new Palavra("INTERNET", "TECNOLOGIA"));

        salvarPalavras(context, palavras);

        return palavras;
    }

}
