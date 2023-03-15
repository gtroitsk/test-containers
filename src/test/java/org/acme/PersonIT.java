package org.acme;

import io.quarkus.test.bootstrap.PostgresqlService;
import io.quarkus.test.bootstrap.RestService;
import io.quarkus.test.scenarios.QuarkusScenario;
import io.quarkus.test.services.Container;
import io.quarkus.test.services.QuarkusApplication;

@QuarkusScenario
public class PersonIT extends CommonTest {
    @Container(image = "docker.io/postgres:13.6", port = 5432, expectedLog = "listening on IPv6 address")
    static PostgresqlService postgres = new PostgresqlService()
            .with("test", "test", "personDB")
            .withProperty("POSTGRES_USER", "test")
            .withProperty("POSTGRES_PASSWORD", "test")
            .withProperty("POSTGRES_DB", "personDB");

    @QuarkusApplication
    static final RestService app = new RestService()
            .withProperty("quarkus.datasource.username", postgres.getUser())
            .withProperty("quarkus.datasource.password", postgres.getPassword())
            .withProperty("quarkus.datasource.jdbc.url", postgres::getJdbcUrl);

}
