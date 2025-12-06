package com.olimpo.olimpo.services;

import com.olimpo.olimpo.dtos.AnomalyDTO;
import com.olimpo.olimpo.dtos.AnomalyType;
import com.olimpo.olimpo.entities.Invoice;
import com.olimpo.olimpo.entities.InvoiceItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class FraudAnalysisService {

    // Tabla mock de precios de referencia (por código)
    private final Map<String, BigDecimal> referencePrices = new HashMap<>();

    public FraudAnalysisService() {
        referencePrices.put("AMOX500", new BigDecimal("2000"));
        referencePrices.put("CONSULTA_EXT", new BigDecimal("25000"));
    }

    public List<AnomalyDTO> analyze(Invoice invoice) {
        List<AnomalyDTO> anomalies = new ArrayList<>();

        detectOverprice(invoice, anomalies);
        detectDuplicates(invoice, anomalies);
        // Aquí podrías agregar más reglas, por ejemplo usando feedback de usuario para "no entregados"

        return anomalies;
    }

    private void detectOverprice(Invoice invoice, List<AnomalyDTO> anomalies) {
        for (InvoiceItem item : invoice.getItems()) {
            BigDecimal ref = referencePrices.get(item.getCode());
            if (ref == null) {
                continue;
            }

            BigDecimal limit = ref.multiply(BigDecimal.valueOf(1.3)); // 30% tolerancia
            if (item.getUnitPrice().compareTo(limit) > 0) {
                AnomalyDTO a = new AnomalyDTO();
                a.setType(AnomalyType.OVERPRICE);
                a.setItemCode(item.getCode());
                a.setMessage("Posible sobreprecio en " + item.getDescription()
                        + ". Precio unitario: " + item.getUnitPrice()
                        + ", referencia: " + ref);
                anomalies.add(a);
            }
        }
    }

    private void detectDuplicates(Invoice invoice, List<AnomalyDTO> anomalies) {
        Map<String, Integer> counts = new HashMap<>();
        for (InvoiceItem item : invoice.getItems()) {
            counts.merge(item.getCode(), 1, Integer::sum);
        }

        for (Map.Entry<String, Integer> e : counts.entrySet()) {
            if (e.getValue() > 1) {
                AnomalyDTO a = new AnomalyDTO();
                a.setType(AnomalyType.DUPLICATE_ITEM);
                a.setItemCode(e.getKey());
                a.setMessage("El ítem con código " + e.getKey()
                        + " aparece " + e.getValue() + " veces. Posible duplicado.");
                anomalies.add(a);
            }
        }
    }
}
