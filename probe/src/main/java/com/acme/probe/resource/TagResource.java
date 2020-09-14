package com.acme.probe.resource;

import com.acme.probe.domain.model.AuditModel;

public class TagResource extends AuditModel {

     private String name;

     public String getName() {
          return name;
     }

     public TagResource setName(String name) {
          this.name = name;
          return this;
     }
}
