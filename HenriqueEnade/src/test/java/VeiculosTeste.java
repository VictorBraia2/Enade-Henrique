import com.fasterxml.jackson.databind.ObjectMapper;
import org.domain.Tipo;
import org.domain.Veiculo;
import org.domain.service.FiltrarTipoVeiculoService;
import org.domain.service.FiltrarVeiculosNovosService;
import org.domain.service.SomarTotalVeiculoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class VeiculosTeste {

    private Tipo tiposVeiculos;

    @BeforeEach
    public void setUp() throws Exception {
        String jsonContent = Files.readString(Paths.get("src/main/java/org/domain/veiculos.json"), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        tiposVeiculos = mapper.readValue(jsonContent, Tipo.class);
    }

    @Test
    public void testFiltrarTipoVeiculoService() {
        String marca = "Chevrolet";
        List<Veiculo> veiculosFiltrados = FiltrarTipoVeiculoService.filtrarPorMarca(tiposVeiculos, marca);

        for (Veiculo veiculo : veiculosFiltrados) {
            assertEquals(marca, veiculo.marca);
        }

        assertNotNull(veiculosFiltrados);
    }

    @Test
    public void testFiltrarVeiculosNovosService() {
        int anoFiltro = 2023;
        List<Veiculo> veiculosFiltrados = FiltrarVeiculosNovosService.filtrarVeiculosPorAno(tiposVeiculos, anoFiltro);

        for (Veiculo veiculo : veiculosFiltrados) {
            assertTrue(veiculo.ano >= anoFiltro);
        }

        assertNotNull(veiculosFiltrados);
    }

    @Test
    public void testSomarTotalVeiculoService() {
        Map<String, Double> somaPorMarca = SomarTotalVeiculoService.calcularSomaValorPorMarca(tiposVeiculos);

        assertNotNull(somaPorMarca);

        assertTrue(somaPorMarca.containsKey("Chevrolet"));
        assertEquals(207000 , somaPorMarca.get("Chevrolet"));

        //OBS: 207.000 corresponde realmente ao valor total de todos os veículos.
    }
}