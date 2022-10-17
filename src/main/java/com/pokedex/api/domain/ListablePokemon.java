package com.pokedex.api.domain;

import lombok.Value;

@Value
public class ListablePokemon {

    private final long id;
    private final String name;

}
