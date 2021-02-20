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

@RestController
@RequestMapping("/parts")
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Location")
public class PartController {

    private final PartService service;

    @Autowired
    public PartController(final PartService service) {
        this.service = service;
    }

    /**
     * @param part is the payload to be saved.
     * @return a request with status
     * <ul>
     *     <li>{@code 202} to inform, that the given part was saved successfully
     *     and a location header pointing to the newly created resource.
     *     </li>
     *     <li>{@code 400}, if the payload is invalid.</li>
     * </ul>
     */
    @PostMapping
    public ResponseEntity<String> savePart(@RequestBody final SimplePart part) {
        try {
            final String uuid = service.save(part);

            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{uuid}")
                    .buildAndExpand(uuid)
                    .toUri())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @return a list of all parts saved previously as a {@link PartList} instance.
     */
    @GetMapping
    public PartList getParts() {
        return service.getParts();
    }

    /**
     * Used to fetch a single part out of the list of all parts.
     *
     * @param uuid specifies the part to be fetched.
     * @return a request with status
     * <ul>
     *    <li>{@code 200} with requested part to inform.</li>
     *    <li>{@code 404}, if there has no part been found.</li>
     *    <li>{@code 400}, if the given uuid is invalid.</li>
     * </ul>
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<Part> getPart(@PathVariable("uuid") final String uuid) {
        try {
            return ResponseEntity.of(service.getPart(uuid));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Used to change certain properties of an existing part by passing the complete new part instance.
     *
     * @param part specifies the whole part to replace the old one with.
     * @return a request with status
     * <ul>
     *    <li>{@code 200} with updated part to inform, that the given part was updated successfully.</li>
     *    <li>{@code 404}, if there has no part been found to update.</li>
     *    <li>{@code 400}, if the payload is invalid.</li>
     * </ul>
     */
    @PutMapping
    public ResponseEntity<Part> updatePart(@RequestBody final Part part) {
        try {
            return ResponseEntity.of(service.update(part));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes a single part out of the list of all parts.
     *
     * @param uuid specifies the part to delete
     * @return a request with status
     * <ul>
     *    <li>{@code 200} with deleted part to inform, that the given part was deleted successfully.</li>
     *    <li>{@code 404}, if there has no part been found to delete.</li>
     *    <li>{@code 400}, if the given uuid is invalid.</li>
     * </ul>
     */
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Part> deletePart(@PathVariable("uuid") final String uuid) {
        try {
            return ResponseEntity.of(service.delete(uuid));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
