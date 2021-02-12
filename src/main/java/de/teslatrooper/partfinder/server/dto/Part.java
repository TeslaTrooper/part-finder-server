package de.teslatrooper.partfinder.server.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.UUID;

public class Part {

    private final String id;
    private final SimplePart part;

    public Part(final String id, final SimplePart part) {
        this.id = id;
        this.part = part;
    }

    @JsonCreator
    public Part(final SimplePart part) {
        this.id = UUID.randomUUID().toString();
        this.part = part;
    }

    public String getId() {
        return id;
    }

    public SimplePart getPart() {
        return part;
    }

}
