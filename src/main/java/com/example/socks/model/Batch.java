package com.example.socks.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private boolean isIncoming;
    @ManyToOne
    @Transient
    private Socks socks;
    private int numberSocks;
}
