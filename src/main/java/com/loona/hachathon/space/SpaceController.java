package com.loona.hachathon.space;

import com.loona.hachathon.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private UserService userService;

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
        return spaceService.getSpaceDto(id);
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


    @PostMapping("/space/{id}/favorite")
    public void addSpacesToFavorite(@PathVariable(value = "id") String id) {
        userService.addSpacesToFavorite(id);
    }

    @DeleteMapping("/space/{id}/favorite")
    public void deleteSpacesFromFavorite(@PathVariable("id") String id) {
        userService.deleteSpacesFromFavorite(id);
    }
}
