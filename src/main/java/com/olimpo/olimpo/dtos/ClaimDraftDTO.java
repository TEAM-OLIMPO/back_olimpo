package com.olimpo.olimpo.dtos;

public class ClaimDraftDTO {

    private String text;

    public ClaimDraftDTO() {
    }

    public ClaimDraftDTO(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
