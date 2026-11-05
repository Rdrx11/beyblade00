package com.example.beyblade00.Dao;

import com.example.beyblade00.Model.BeybladeModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BeybladeDao {
    private static final Path RUTA_ARCHIVO = Path.of("beyblade.json");

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void guardar (List<BeybladeModel> lista) throws IOException {
        String json = gson.toJson(lista);
        Files.writeString(RUTA_ARCHIVO, json, StandardCharsets.UTF_8);
    }

    public List<BeybladeModel> cargar (){
        if (!Files.exists(RUTA_ARCHIVO)){
            return new ArrayList<>();
        }
        try {
            String json = Files.readString(RUTA_ARCHIVO, StandardCharsets.UTF_8);
            Type tipoLista = new TypeToken<ArrayList<BeybladeModel>>() {}.getType();
            List<BeybladeModel> lista = gson.fromJson(json, tipoLista);
            return (lista != null) ? lista : new ArrayList<>();
        }catch (IOException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
