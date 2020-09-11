package de.teslatrooper.partfinder.server.controller;

import de.teslatrooper.partfinder.server.dto.Attribute;
import de.teslatrooper.partfinder.server.dto.Part;
import de.teslatrooper.partfinder.server.dto.PartList;
import de.teslatrooper.partfinder.server.dto.SimplePart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PartControllerTest {

    private static final String HOST = "http://localhost:";
    private static final String PATH = "/part-finder-server/parts";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Test
    public void getPartsShouldReturnNonEmptyList() {
        PartList parts = template.getForObject(HOST + port + PATH, PartList.class);

        assertThat(parts).isNotNull();
    }

    @Test
    public void savePartShouldReturnIdAsString() {
        ResponseEntity<String> response = template.postForEntity(HOST + port + PATH, createMockPart(), String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        String uuid = UUID.fromString(response.getBody()).toString();

        Part part = template.getForObject(HOST + port + PATH + "/" + uuid, Part.class);
        assertThat(part).isNotNull();
    }

    @Nested
    @DisplayName("Test for altering parts")
    class AlteringPartTest {

        @BeforeEach
        @DisplayName("Save mock part...")
        public void saveMockPart() {
            savePartShouldReturnIdAsString();
        }

        @Test
        @DisplayName("Delete mock part...")
        public void deletePartShouldRemoveExistingPart() {
            PartList parts = template.getForObject(HOST + port + PATH, PartList.class);
            Part partToDelete = parts.getParts().get(0);
            template.delete(HOST + port + PATH + "/" + partToDelete.getId());

            Part deletedPart = template.getForObject(HOST + port + PATH + "/" + partToDelete.getId(), Part.class);
            assertThat(deletedPart).isNull();
        }

        @Test
        @DisplayName("Update mock part...")
        public void putPartShouldReturnOldElement() {
            PartList parts = template.getForObject(HOST + port + PATH, PartList.class);
            Part oldPart = parts.getParts().get(0);
            Part newPart = createUpdatedMockPart(oldPart);

            template.put(HOST + port + PATH, newPart);

            Part updatedPart = template.getForObject(HOST + port + PATH + "/" + oldPart.getId(), Part.class);

            assertThat(updatedPart.getPart().getLocation()).isEqualTo(newPart.getPart().getLocation());
        }

    }

    private SimplePart createMockPart() {
        return new SimplePart("Schraube", "C5", 10, new Attribute[]{new Attribute("Kopf", "Kreuz")});
    }

    private Part createUpdatedMockPart(final Part ref) {
        final SimplePart refSimplePart = ref.getPart();

        return new Part(ref.getId(), new SimplePart(refSimplePart.getName(), "A3", refSimplePart.getQty(),
                refSimplePart.getAttributes()));
    }

}
