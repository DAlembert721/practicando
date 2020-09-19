package com.acme.probe.resource;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

public class SaveCommentResource {

    public String getText() {
        return text;
    }

    public SaveCommentResource setText(String text) {
        this.text = text;
        return this;
    }

    @NotNull
    @Lob
    private String text;


}
