package com.loona.hachathon.space;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @GetMapping("/spaces")
    public List<SpaceResponseDto> getSpaces() {
        return spaceService.getSpaces();
    }

    @GetMapping("/spaces/my")
    public List<SpaceResponseDto> getMySpaces() {
        return spaceService.getMySpaces();
    }

    @GetMapping("/space/{id}")
    public SpaceResponseDto getSpaces(@PathVariable(value = "id", required = false) String id) {
        return spaceService.getSpace(id);
    }

    @PostMapping("/space")
    public void createSpace(@RequestBody SpaceDto spaceDto) {
        spaceService.saveSpace(SpaceConverter.convert(spaceDto));
    }

    @PutMapping("/space/{id}")
    public void updateSpace(@PathVariable("id") String id, @RequestBody SpaceDto spaceDto) {
        spaceService.updateSpace(id, SpaceConverter.convert(spaceDto));
    }

    @DeleteMapping("/space/{id}")
    public void deleteSpace(@PathVariable("id") String id) {
        spaceService.deleteSpace(id);
    }
}
