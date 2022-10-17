# KLM Recruitment case (Medium & Senior)
## Build a pokedex!

During this live coding session you will be building a pokedex!

The case will consist of the following external components:

- The [Pokemon API](https://pokeapi.co/), a free online rest API to retrieve everything you wanted to know about Pokemon!
- The [batch file](src/main/resources/pokemon-batch.json) that will contain the initial load
- The database, an in memory H2 instance used to store our data

## Assignment

### Data

- Load all the data from the [batch file](src/main/resources/pokemon-batch.json) into the database.

*Extra assignment for senior candidates*
- Schedule the data loading job, so that the data is loaded every minute
  - Since the ID's are auto generated find a way to load the data *without inserting duplicates* 

### Pokemon Service

- Create a client to connect to the Pokemon API, every database entry contains the link to be used to connect to the Pokemon API.
  - The **only** information you need from that source is base_experience, height and weight.
- Retrieve the required data from the database
  - Use Spring JDBC's CrudRepository
- Combine both to end up with the resulting object below

The result should be a **Pokemon** object with the fields below:

- long id
- String name;
- int baseExperience;
- int height;
- int weight;

### Controllers

Create a controller to handle the requests below:

- /pokemon
  - list all pokemon from the database
  - Collection of objects that only contain id & name.
- /pokemon?query=
  - list all pokemon that match the query (*by **partial** name*)
  - Collection of objects that only contain id & name.
- /pokemon/{id}
  - retrieve full pokemon details for a single pokemon
  - Single object containing all fields (id, name, baseExperience, height & weight)