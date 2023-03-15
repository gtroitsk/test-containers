package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(PostgresTestProfile.class)
public class PersonTestContainerTest extends CommonTest {

}
