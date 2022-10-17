package com.pokedex.api;

import com.pokedex.api.domain.ListablePokemon;
import com.pokedex.api.domain.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class PokemonControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RestTemplate restTemplateToMock;

    @LocalServerPort
    private int port;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void initTest() {
        mockServer = MockRestServiceServer.createServer(restTemplateToMock);
    }

    @PostConstruct
    public void waitForBatchToLoad() throws InterruptedException {
        Thread.sleep(1000L); //wait for batch to finish
    }

    @Test
    @DisplayName("/pokemon : List all results")
    void testListAll() {
        ListablePokemon[] results = restTemplate.getForObject(format("http://localhost:%s/pokemon", port), ListablePokemon[].class);
        assertEquals(1154, results.length);
    }

    @Test
    @DisplayName("/pokemon?query=bulba : Query by partial name")
    void testQueryByName() {
        ListablePokemon[] results = restTemplate.getForObject(format("http://localhost:%s/pokemon?query=bulba", port), ListablePokemon[].class);
        assertEquals(1, results.length);
        assertEquals("bulbasaur", results[0].getName());
    }

    @Test
    @DisplayName("/pokemon/1 : list by ID")
    void testById() throws URISyntaxException {
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("https://pokeapi.co/api/v2/pokemon/1/")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\"base_experience\": 64,\"height\": 7,\"weight\": 69}")
                );


        Pokemon bulbasaur = restTemplate.getForObject(format("http://localhost:%s/pokemon/1", port), Pokemon.class);
        assertEquals(1, bulbasaur.getId());
        assertEquals("bulbasaur", bulbasaur.getName());
        assertEquals(69, bulbasaur.getWeight());
        assertEquals(7, bulbasaur.getHeight());
        assertEquals(64, bulbasaur.getBaseExperience());
    }

}
