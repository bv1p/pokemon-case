package com.pokedex.api.repository;

import org.springframework.data.repository.CrudRepository;

public interface PokemonRepository extends CrudRepository<PokemonReference, Long> {

}
