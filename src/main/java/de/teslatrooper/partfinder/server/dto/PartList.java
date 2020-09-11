package de.teslatrooper.partfinder.server.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PartList {

    private final List<Part> parts;

    public PartList() {
        this(new ArrayList<>());
    }

    public PartList(final Collection<Part> parts) {
        this.parts = new ArrayList<>(parts);
    }

    public List<Part> getParts() {
        return parts;
    }

}
