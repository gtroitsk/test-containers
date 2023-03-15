package org.acme;

import io.quarkus.test.bootstrap.PostgresqlService;
import io.quarkus.test.bootstrap.RestService;
import io.quarkus.test.scenarios.QuarkusScenario;
import io.quarkus.test.services.Container;
import io.quarkus.test.services.QuarkusApplication;

@QuarkusScenario
public class PersonTestFrameworkIT extends CommonTestCases {
    @Container(image = "docker.io/postgres:15", port = 5432, expectedLog = "listening on IPv6 address")
    static PostgresqlService postgres = new PostgresqlService()
            .withProperty("POSTGRES_USER", "test")
            .withProperty("POSTGRES_PASSWORD", "test")
            .withProperty("POSTGRES_DB", "personDB")
            .withProperty("PGDATA", "/tmp/psql");

    @QuarkusApplication
    static final RestService app = new RestService()
            .withProperty("quarkus.datasource.username", postgres.getUser())
            .withProperty("quarkus.datasource.password", postgres.getPassword())
            .withProperty("quarkus.datasource.jdbc.url", postgres::getJdbcUrl);

}
