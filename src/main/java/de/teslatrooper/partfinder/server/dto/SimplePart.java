package de.teslatrooper.partfinder.server.dto;

public class SimplePart {

    private final String name;
    private final String location;
    private final int qty;
    private final Attribute[] attributes;

    public SimplePart(final String name, final String location, final int qty, final Attribute[] attributes) {
        this.name = name;
        this.location = location;
        this.qty = qty;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getQty() {
        return qty;
    }

    public Attribute[] getAttributes() {
        return attributes;
    }

}
