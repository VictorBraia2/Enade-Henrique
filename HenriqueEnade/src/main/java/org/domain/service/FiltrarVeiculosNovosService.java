package org.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.domain.Categoria;
import org.domain.Tipo;
import org.domain.Veiculo;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FiltrarVeiculosNovosService {
    public static void main(String[] args) {
        try {

            String jsonContent = Files.readString(Paths.get("src/main/java/org/domain/veiculos.json"), StandardCharsets.UTF_8);


            ObjectMapper mapper = new ObjectMapper();
            Tipo tiposVeiculos = mapper.readValue(jsonContent, Tipo.class);


            int anoFiltro = 2023;
            List<Veiculo> veiculosFiltrados = filtrarVeiculosPorAno(tiposVeiculos, anoFiltro);


            veiculosFiltrados.forEach(veiculo -> {
                System.out.println("ID: " + veiculo.id);
                System.out.println("Marca: " + veiculo.marca);
                System.out.println("Modelo: " + veiculo.modelo);
                System.out.println("Ano: " + veiculo.ano);
                System.out.println("Valor: " + veiculo.valor);
                System.out.println("----------");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Veiculo> filtrarVeiculosPorAno(Tipo tiposVeiculos, int anoFiltro) {
        List<Veiculo> veiculosFiltrados = new ArrayList<>();


        if (tiposVeiculos.carros != null) {
            for (Categoria categoria : tiposVeiculos.carros) {
                if (categoria.novos != null) {
                    veiculosFiltrados.addAll(categoria.novos.stream()
                            .filter(veiculo -> veiculo.ano >= anoFiltro)
                            .collect(Collectors.toList()));
                }
            }
        }

        if (tiposVeiculos.motos != null) {
            for (Categoria categoria : tiposVeiculos.motos) {
                if (categoria.novos != null) {
                    veiculosFiltrados.addAll(categoria.novos.stream()
                            .filter(veiculo -> veiculo.ano >= anoFiltro)
                            .collect(Collectors.toList()));
                }
            }
        }

        return veiculosFiltrados;
    }
}
