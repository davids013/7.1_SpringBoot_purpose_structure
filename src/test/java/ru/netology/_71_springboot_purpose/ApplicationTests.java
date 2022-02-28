package ru.netology._71_springboot_purpose;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers(disabledWithoutDocker = true)
class ApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;
    public static GenericContainer<?> devapp = new GenericContainer<>("devapp").withExposedPorts(8080);
    public static GenericContainer<?> prodapp = new GenericContainer<>("prodapp").withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devapp.start();
        prodapp.start();
    }

    @Test
    void devTest() {
        test(devapp, 8080, "Current profile is dev");
    }

    @Test
    void prodTest() {
        test(prodapp, 8081, "Current profile is production");
    }

    private void test(GenericContainer<?> container, int port, String message) {
        final ResponseEntity<String> forEntity = restTemplate.getForEntity(
                "http://localhost:" + container.getMappedPort(port) + "/profile", String.class);
        final String response = forEntity.getBody();
        System.out.println(container.getDockerImageName() + " -> \"" + response + "\"");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response, message);
    }

}
