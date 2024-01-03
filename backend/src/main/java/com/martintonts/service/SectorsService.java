package com.martintonts.service;

import com.martintonts.dto.SectorDTO;
import com.martintonts.dto.UserSectorsDTO;
import com.martintonts.model.Sector;
import com.martintonts.model.UserSectors;
import com.martintonts.repository.SectorsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SectorsService {

    private final SectorsRepository sectorsRepository;
    public void saveSector(SectorDTO sectorDTO) {
        if (Objects.nonNull(sectorDTO)) {
            Sector sectorFromDb = sectorsRepository.findSectorByVal(sectorDTO.getVal());
            if (sectorFromDb != null) {// existing
                sectorFromDb.setVal(sectorDTO.getVal());
                sectorFromDb.setPos(sectorDTO.getPos());
                sectorFromDb.setName(sectorDTO.getName());
                sectorFromDb.setLevel(sectorDTO.getLevel());
                sectorsRepository.save(sectorFromDb);
            } else {// new
                Sector sector = new Sector();
                sector.setVal(sectorDTO.getVal());
                sector.setPos(sectorDTO.getPos());
                sector.setName(sectorDTO.getName());
                sector.setLevel(sectorDTO.getLevel());
                sectorsRepository.save(sector);
            }
        }
    }

    /**
     * return all sectors
     * @return
     */
    public List<Sector> getAll() {
        return sectorsRepository.findAll()
                .stream().sorted(Comparator.comparingInt(Sector::getPos)).toList();
    }
}
