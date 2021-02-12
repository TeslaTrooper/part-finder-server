package de.teslatrooper.partfinder.server.controller;

import de.teslatrooper.partfinder.server.dto.Part;
import de.teslatrooper.partfinder.server.dto.PartList;
import de.teslatrooper.partfinder.server.dto.SimplePart;
import de.teslatrooper.partfinder.server.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/parts")
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Location")
public class PartController {

    private final PartService service;

    @Autowired
    public PartController(final PartService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> savePart(@RequestBody final SimplePart part) {
        final String uuid = service.save(part);

        if (uuid == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{uuid}").buildAndExpand(uuid).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public PartList getParts() {
        return service.getParts();
    }

    @GetMapping("/{uuid}")
    public Part getPart(@PathVariable("uuid") final String uuid) {
        return service.getPart(uuid);
    }

    @PutMapping
    public void updatePart(@RequestBody final Part part) {
        service.update(part);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Part> deletePart(@PathVariable("uuid") final String uuid) {
        final Optional<Part> deletedPart = service.delete(uuid);

        return ResponseEntity.of(deletedPart);
    }

}
