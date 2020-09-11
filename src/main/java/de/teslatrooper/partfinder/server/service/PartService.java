package de.teslatrooper.partfinder.server.service;

import de.teslatrooper.partfinder.server.dto.Part;
import de.teslatrooper.partfinder.server.dto.PartList;
import de.teslatrooper.partfinder.server.dto.SimplePart;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PartService {

    private final Map<String, Part> parts;

    public PartService() {
        parts = new HashMap<>();
    }

    public String save(final SimplePart simplePart) {
        if (simplePart == null)
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

    public Part delete(final String id) {
        return parts.remove(id);
    }

}
