package com.pokedex.api.repository;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class PokemonReference {

    @Id
    private String name;
    private String url;

}
