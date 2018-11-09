package com.loona.hachathon.space;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/spaces")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @GetMapping("/{id}")
    public List<Space> getSpaces(@PathVariable(value = "id", required = false) String id) {
        return spaceService.getSpaces(id);
    }

    @PostMapping
    public void createSpace(@RequestBody SpaceDto spaceDto) {
        spaceService.saveSpace(SpaceConverter.convert(spaceDto));
    }

    @PutMapping("/{id}")
    public void updateSpace(@PathVariable("id") String id, @RequestBody SpaceDto spaceDto) {
        spaceService.updateSpace(id, SpaceConverter.convert(spaceDto));
    }

    @DeleteMapping("/{id}")
    public void deleteSpace(@PathVariable("id") String id) {
        spaceService.deleteSpace(id);
    }
}
