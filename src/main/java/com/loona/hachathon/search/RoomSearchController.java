package com.loona.hachathon.search;

import com.loona.hachathon.exception.InvalidInputParametersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/rooms/search")
public class RoomSearchController {

    @Autowired
    private RoomSearchService roomSearchService;

    @GetMapping
    public RoomSearchResultDto searchRoom(
            @RequestParam(name = "roomType") String roomType,
            @RequestParam(name = "minPrice", required = false, defaultValue = "100") Integer minPrice,
            @RequestParam(name = "maxPrice", required = false, defaultValue = "5000") Integer maxPrice,
            @RequestParam(name = "rentType", required = false, defaultValue = "DAYS") String rentType,
            @RequestParam(name = "minFootage", required = false, defaultValue = "5") Integer minFootage,
            @RequestParam(name = "maxFootage", required = false, defaultValue = "100") Integer maxFootage,
            @RequestParam(name = "options", required = false) List<String> options,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "100") Integer pageSize) {

        RoomFilterParams filterParams = new RoomFilterParams(roomType, minPrice, maxPrice, rentType, minFootage,
                maxFootage, options, page, pageSize);

        if (!RoomFilterParamsValidator.validate(filterParams)) throw new InvalidInputParametersException();

        return roomSearchService.search(filterParams);
    }

}
