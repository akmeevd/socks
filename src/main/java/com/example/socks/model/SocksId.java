package com.example.socks.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class SocksId {
    private String color;
    private byte cottonPart;
}
