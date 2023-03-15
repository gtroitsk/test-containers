package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import org.acme.resources.PostgresTestProfile;

@QuarkusTest
@TestProfile(PostgresTestProfile.class)
public class PersonTestContainerTest extends CommonTestCases {

}
