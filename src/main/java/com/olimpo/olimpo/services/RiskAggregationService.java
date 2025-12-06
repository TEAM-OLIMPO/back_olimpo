package com.olimpo.olimpo.services;

import com.olimpo.olimpo.entities.Invoice;
import org.springframework.stereotype.Service;

/**
 * Recibe facturas anonimizadas y las "envía" a un cerebro de IA.
 * Por ahora es un stub. Luego aquí puedes mandar a Kafka, BigQuery, etc.
 */
@Service
public class RiskAggregationService {

    public void sendAnonymized(Invoice invoice) {
        // Aquí deberías:
        // 1. Quitar patientId y otros datos personales.
        // 2. Enviar a tu data lake / cola / servicio analítico.

        System.out.println(
                "Enviando factura anonimizada de IPS " +
                invoice.getProviderName() +
                " al cerebro de riesgo..."
        );
    }
}
