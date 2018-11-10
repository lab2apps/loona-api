package com.loona.hachathon.search;

import com.loona.hachathon.space.SpaceResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/space/search")
public class SpaceSearchController {

    @Autowired
    private SpaceSearchService spaceSearchService;

    @GetMapping
    public List<SpaceResponseDto> search(@RequestParam(name = "spaceName") String spaceName) {
        return spaceSearchService.search(spaceName);
    }

}
