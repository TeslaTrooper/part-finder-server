package de.teslatrooper.partfinder.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.teslatrooper.partfinder.server.dto.Part;
import de.teslatrooper.partfinder.server.dto.PartList;
import de.teslatrooper.partfinder.server.dto.SimplePart;
import de.teslatrooper.partfinder.server.service.PartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PartController.class)
@AutoConfigureMockMvc
public class PartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartService service;

    @Before
    public void setup() {
        PartList parts = new PartList();
        Part part = new Part(new SimplePart("", "", 0));

        Mockito.when(service.save(Mockito.any(SimplePart.class))).thenReturn(UUID.randomUUID().toString());
        Mockito.when(service.getPart(Mockito.anyString())).thenReturn(Optional.of(part));
        Mockito.when(service.getParts()).thenReturn(parts);
        Mockito.when(service.update(Mockito.any(Part.class))).thenReturn(Optional.of(part));
        Mockito.when(service.delete(Mockito.anyString())).thenReturn(Optional.of(part));
    }

    @Test
    public void testGetPart() throws Exception {
        this.mockMvc.perform(get("/parts/some-id").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetParts() throws Exception {
        this.mockMvc.perform(get("/parts").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testSavePart() throws Exception {
        String payload = asJsonString(new SimplePart("", "", 0));

        this.mockMvc.perform(post("/parts")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isCreated())
                .andExpect(header().exists("LOCATION"));
    }

    @Test
    public void testUpdatePart() throws Exception {
        String payload = asJsonString(new Part(new SimplePart("", "", 0)));

        this.mockMvc.perform(put("/parts")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePart() throws Exception {
        this.mockMvc.perform(delete("/parts/some-id")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

}
