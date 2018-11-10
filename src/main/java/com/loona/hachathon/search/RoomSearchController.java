package com.loona.hachathon.search;

import com.loona.hachathon.exception.InvalidInputParametersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/rooms/search")
public class RoomSearchController {

    @Autowired
    private RoomSearchService roomSearchService;

    @GetMapping
    public RoomSearchResultDto searchRoom(
            @RequestParam(name = "startWorkTime")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startWorkTime,
            @RequestParam(name = "endWorkTime")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime endWorkTime,
            @RequestParam(name = "roomType", required = false, defaultValue = "") String roomType,
            @RequestParam(name = "rentType", required = false, defaultValue = "") String rentType,
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "minPrice", required = false, defaultValue = "0") Integer minPrice,
            @RequestParam(name = "maxPrice", required = false, defaultValue = "100000") Integer maxPrice,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "100") Integer pageSize) {

        RoomFilterParams filterParams = new RoomFilterParams(startWorkTime, endWorkTime, roomType, rentType, name,
                minPrice, maxPrice, page, pageSize);

        if (!RoomFilterParamsValidator.validate(filterParams)) throw new InvalidInputParametersException();

        return roomSearchService.search(filterParams);
    }

}
