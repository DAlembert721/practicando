package com.acme.blogging.domain.model;



import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String title;

    @NotNull
    private String description;

    @NotNull
    @Lob
    private String content;



}
