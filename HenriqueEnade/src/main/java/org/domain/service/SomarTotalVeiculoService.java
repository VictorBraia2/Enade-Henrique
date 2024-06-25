package org.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.domain.Categoria;
import org.domain.Tipo;
import org.domain.Veiculo;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SomarTotalVeiculoService {
    public static void main(String[] args) {
        try {

            String jsonContent = Files.readString(Paths.get("src/main/java/org/domain/veiculos.json"), StandardCharsets.UTF_8);


            ObjectMapper mapper = new ObjectMapper();
            Tipo tiposVeiculos = mapper.readValue(jsonContent, Tipo.class);


            Map<String, Double> somaPorMarca = calcularSomaValorPorMarca(tiposVeiculos);


            somaPorMarca.forEach((marca, soma) -> {
                System.out.println("Marca: " + marca);
                System.out.println("Soma total de valor: " + soma);
                System.out.println("----------");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Map<String, Double> calcularSomaValorPorMarca(Tipo tiposVeiculos) {
        Map<String, Double> somaPorMarca = new HashMap<>();


        if (tiposVeiculos.carros != null) {
            for (Categoria categoria : tiposVeiculos.carros) {
                if (categoria.novos != null) {
                    for (Veiculo veiculo : categoria.novos) {
                        somaPorMarca.merge(veiculo.marca, veiculo.valor, Double::sum);
                    }
                }
            }
        }


        if (tiposVeiculos.motos != null) {
            for (Categoria categoria : tiposVeiculos.motos) {
                if (categoria.novos != null) {
                    for (Veiculo veiculo : categoria.novos) {
                        somaPorMarca.merge(veiculo.marca, veiculo.valor, Double::sum);
                    }
                }
            }
        }

        return somaPorMarca;
    }
}
