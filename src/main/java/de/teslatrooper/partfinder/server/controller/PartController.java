package de.teslatrooper.partfinder.server.controller;

import de.teslatrooper.partfinder.server.dto.Part;
import de.teslatrooper.partfinder.server.dto.PartList;
import de.teslatrooper.partfinder.server.dto.SimplePart;
import de.teslatrooper.partfinder.server.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PartController {

    private final PartService service;

    @Autowired
    public PartController(final PartService service) {
        this.service = service;
    }

    @PostMapping("/parts")
    public String savePart(@RequestBody final SimplePart part) {
        return service.save(part);
    }

    @GetMapping(value = "/parts")
    public PartList getParts() {
        return service.getParts();
    }

    @GetMapping(value="/parts/{uuid}")
    public Part getPart(@PathVariable("uuid") final String uuid) {
        return service.getPart(uuid);
    }

    @PutMapping("/parts")
    public void updatePart(@RequestBody final Part part) {
        service.update(part);
    }

    @DeleteMapping("/parts/{uuid}")
    public void deletePart(@PathVariable("uuid") final String uuid) {
        service.delete(uuid);
    }

}
