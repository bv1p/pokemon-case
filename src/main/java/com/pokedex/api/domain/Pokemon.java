package com.pokedex.api.domain;

import lombok.Value;

@Value
public class Pokemon {

    private final long id;
    private final String name;
    private final int baseExperience;
    private final int height;
    private final int weight;

}
