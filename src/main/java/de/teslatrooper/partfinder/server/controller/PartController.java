package de.teslatrooper.partfinder.server.controller;

import de.teslatrooper.partfinder.server.dto.Attribute;
import de.teslatrooper.partfinder.server.dto.Part;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class PartController {

    @GetMapping("/parts")
    public List<Part> getParts() {
        final Part part = new Part("5", "Schraube", "C5", 15, new Attribute[]{new Attribute("Kopf", "Torx")});
        return Collections.singletonList(part);
    }

}
