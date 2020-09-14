package com.acme.probe.resource;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SaveTagResource {
    @NotNull
    @Size(max = 100)
    private String name;
}
