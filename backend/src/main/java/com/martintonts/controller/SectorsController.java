package com.martintonts.controller;

import com.martintonts.dto.SectorDTO;
import com.martintonts.dto.UserSectorsDTO;
import com.martintonts.model.Sector;
import com.martintonts.service.SectorsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sectors")
@RequiredArgsConstructor
public class SectorsController {
    private final SectorsService sectorsService;
    @PostMapping
    public void save(@RequestBody SectorDTO sectorDTO) {
        sectorsService.saveSector(sectorDTO);
    }

    @GetMapping
    public List<Sector> getAll() {
        return sectorsService.getAll();
    }
}
