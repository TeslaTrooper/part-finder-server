package de.teslatrooper.partfinder.server.service;

import de.teslatrooper.partfinder.server.dto.Attribute;
import de.teslatrooper.partfinder.server.dto.Part;
import de.teslatrooper.partfinder.server.dto.PartList;
import de.teslatrooper.partfinder.server.dto.SimplePart;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PartService {

    private final Map<String, Part> parts;

    public PartService() {
        parts = new HashMap<>();

        Part p1 = new Part(new SimplePart("Schraube", "C3", 5));
        Part p2 = new Part(new SimplePart("Mutter", "D1", 3, new Attribute[]{new Attribute("Größe", "M8")}));

        parts.put(p1.getId(), p1);
        parts.put(p2.getId(), p2);
    }

    public String save(final SimplePart simplePart) {
        validate(simplePart);

        Part part = new Part(simplePart);
        parts.put(part.getId(), part);

        return part.getId();
    }

    public PartList getParts() {
        return new PartList(parts.values());
    }

    public Optional<Part> getPart(final String uuid) {
        validate(uuid);
        return Optional.ofNullable(parts.get(uuid));
    }

    public Optional<Part> update(final Part part) {
        validate(part);
        return Optional.ofNullable(parts.replace(part.getId(), part));
    }

    public Optional<Part> delete(final String uuid) {
        validate(uuid);
        return Optional.ofNullable(parts.remove(uuid));
    }

    private void validate(final Part part) throws IllegalArgumentException {
        validate(part.getId());
        validate(part.getPart());
    }

    private void validate(final SimplePart simplePart) throws IllegalArgumentException {
        if (simplePart == null || simplePart.getName() == null || simplePart.getLocation() == null)
            throw new IllegalArgumentException("Given simple part has missing properties!");
    }

    private void validate(final String uuid) throws IllegalArgumentException {
        if (uuid == null)
            throw new IllegalArgumentException("Given uuid must not be null!");

        UUID.fromString(uuid); // Throws exception implicitly
    }

}
