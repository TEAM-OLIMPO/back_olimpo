package com.olimpo.olimpo.services;

import com.olimpo.olimpo.dtos.AnomalyDTO;
import com.olimpo.olimpo.dtos.ClaimDraftDTO;
import com.olimpo.olimpo.entities.Invoice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimService {

    public ClaimDraftDTO generateClaim(Invoice invoice, List<AnomalyDTO> anomalies) {
        String anomaliesText = anomalies.stream()
                .map(a -> "- " + a.getMessage())
                .collect(Collectors.joining("\n"));

        String text = """
                Señor(es) EPS / IPS correspondiente:

                Por medio de la presente solicito la revisión de la factura asociada a la atención
                prestada por la IPS: %s (NIT %s), con valor total aproximado de %s.

                Durante la revisión automática de la factura se detectaron las siguientes posibles
                inconsistencias:

                %s

                Solicito amablemente se verifique la pertinencia médica, la correcta entrega de
                medicamentos y la aplicación de los tarifarios y acuerdos vigentes, así como la
                devolución de los cobros que no correspondan.

                Atentamente,

                [Nombre del paciente]
                [Documento del paciente]
                """.formatted(
                invoice.getProviderName(),
                invoice.getProviderNit(),
                invoice.getTotalAmount(),
                anomaliesText.isBlank()
                        ? "- No se encontraron anomalías relevantes, se solicita verificación de rutina."
                        : anomaliesText
        );

        return new ClaimDraftDTO(text);
    }
}
