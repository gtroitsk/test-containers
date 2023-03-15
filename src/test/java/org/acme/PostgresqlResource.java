package org.acme;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class PostgresqlResource implements QuarkusTestResourceLifecycleManager {

    private GenericContainer<?> postgresContainer;

    @Override
    public Map<String, String> start() {
        postgresContainer = new GenericContainer<>(DockerImageName.parse("docker.io/postgres:13.6"))
                .withEnv("POSTGRES_USER", "test")
                .withEnv("POSTGRES_PASSWORD", "test")
                .withEnv("POSTGRES_DB", "personDB")
                .withExposedPorts(5432);

        postgresContainer.waitingFor(new HostPortWaitStrategy()).waitingFor(
                Wait.forLogMessage(".*listening on IPv6.*", 1)
                        .withStartupTimeout(Duration.ofMinutes(3))).start();

        Map<String, String> config = new HashMap<>();
        config.put("quarkus.datasource.username", "test");
        config.put("quarkus.datasource.password", "test");
        config.put("quarkus.datasource.jdbc.url", String.format("jdbc:postgresql://%s:%d/personDB",
                postgresContainer.getHost(), postgresContainer.getFirstMappedPort()));

        return config;
    }

    @Override
    public void stop() {
        if (postgresContainer != null) {
            postgresContainer.stop();
        }
    }
}