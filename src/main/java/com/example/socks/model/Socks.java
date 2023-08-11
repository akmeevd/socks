package com.example.socks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Socks {

    @EmbeddedId
    private SocksId socksId;
    private int quantity;
    @OneToMany
    @JsonIgnore
    private List<Batch> batch;
}
