package de.teslatrooper.partfinder.server.service;

import de.teslatrooper.partfinder.server.dto.Attribute;
import de.teslatrooper.partfinder.server.dto.Part;
import de.teslatrooper.partfinder.server.dto.PartList;
import de.teslatrooper.partfinder.server.dto.SimplePart;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        if (simplePart == null || simplePart.getName() == null || simplePart.getLocation() == null)
            return null;

        Part part = new Part(simplePart);
        parts.put(part.getId(), part);

        return part.getId();
    }

    public PartList getParts() {
        return new PartList(parts.values());
    }

    public Part getPart(final String uuid) {
        return parts.get(uuid);
    }

    public Part update(final Part part) {
        return parts.replace(part.getId(), part);
    }

    public Optional<Part> delete(final String id) {
        if (id == null)
            return Optional.empty();

        Part removedPart = parts.remove(id);
        if (removedPart == null)
            return Optional.empty();

        return Optional.of(removedPart);
    }

}
