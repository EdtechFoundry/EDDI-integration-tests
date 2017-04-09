package ai.labs.testing.integration;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

/**
 * @author ginccc
 */
public class RestBehaviorTest extends BaseCRUDOperations {
    private static final String ROOT_PATH = "/behaviorstore/behaviorsets/";
    private static final String RESOURCE_URI = "eddi://ai.labs.behavior" + ROOT_PATH;

    private String TEST_JSON;
    private String TEST_JSON2;

    @BeforeTest
    public void setup() throws IOException {
        super.setup();

        // load test resources
        TEST_JSON = load("behavior/createBehavior.json");
        TEST_JSON2 = load("behavior/updateBehavior.json");
    }

    @Test()
    public void createBehavior() {
        create(TEST_JSON, ROOT_PATH, RESOURCE_URI);
    }

    @Test(dependsOnMethods = "createBehavior")
    public void readBehavior() {
        read(ROOT_PATH).
                body("behaviorGroups[0].name", equalTo("Smalltalk")).
                body("behaviorGroups[0].behaviorRules[0].children[0].type", equalTo("negation"));
    }

    @Test(dependsOnMethods = "readBehavior")
    public void updateBehavior() {
        update(TEST_JSON2, ROOT_PATH, RESOURCE_URI).
                body("behaviorGroups[0].behaviorRules[0].name", equalTo("Welcome_changed"));
    }

    @Test(dependsOnMethods = "updateBehavior")
    public void deleteBehavior() {
        delete(ROOT_PATH);
    }
}
