package com.olimpo.olimpo.dtos;

import java.util.List;

public class InvoiceAnalysisResponse {

    private InvoiceDTO invoice;
    private List<AnomalyDTO> anomalies;
    private ClaimDraftDTO claimDraft;

    public InvoiceDTO getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceDTO invoice) {
        this.invoice = invoice;
    }

    public List<AnomalyDTO> getAnomalies() {
        return anomalies;
    }

    public void setAnomalies(List<AnomalyDTO> anomalies) {
        this.anomalies = anomalies;
    }

    public ClaimDraftDTO getClaimDraft() {
        return claimDraft;
    }

    public void setClaimDraft(ClaimDraftDTO claimDraft) {
        this.claimDraft = claimDraft;
    }
}
