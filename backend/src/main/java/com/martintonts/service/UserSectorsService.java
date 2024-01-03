package com.martintonts.service;

import com.martintonts.dto.UserSectorsDTO;
import com.martintonts.model.Sector;
import com.martintonts.model.UserSectors;
import com.martintonts.repository.SectorsRepository;
import com.martintonts.repository.UserSectorsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSectorsService {
    private final UserSectorsRepository userSectorsRepository;
    private final SectorsRepository sectorsRepository;

    public UserSectors saveUserSectors(UserSectorsDTO userSectorsDTO) {
        if (Objects.nonNull(userSectorsDTO)) {
            List<Sector> sectorsFromDb = sectorsRepository.findAllById(
                    userSectorsDTO.getSectors().stream().map(sectorDTO ->
                            Long.parseLong(sectorDTO.getId())).toList()
            );
            if (userSectorsDTO.getId() != null) {// existing userSectors
                Optional<UserSectors> optionalUserSectorsFromDb = userSectorsRepository
                        .findById(Long.parseLong(userSectorsDTO.getId()));
                if (optionalUserSectorsFromDb.isPresent()) {
                    UserSectors userSectorsFromDb = optionalUserSectorsFromDb.get();
                    if (isChanged(userSectorsFromDb, userSectorsDTO)) {
                        clearSectors(userSectorsFromDb);
                        sectorsFromDb.forEach(sector -> {
                            List<UserSectors> userSectors = sector.getUserSectors();
                            userSectors.add(userSectorsFromDb);
                            sector.setUserSectors(userSectors);
                            sectorsRepository.save(sector);
                        });
                        userSectorsFromDb.setSectors(sectorsFromDb);
                        userSectorsFromDb.setName(userSectorsDTO.getName());
                        userSectorsFromDb.setAgreeToTerms(userSectorsDTO.isAgreeToTerms());
                        return userSectorsRepository.save(userSectorsFromDb);
                    } else return userSectorsFromDb;// if no change return without saving
                }
            } else {// new userSectors
                UserSectors newUserSectors = UserSectors.builder()
                        .name(userSectorsDTO.getName())
                        .sectors(sectorsFromDb)
                        .agreeToTerms(userSectorsDTO.isAgreeToTerms())
                        .build();
                UserSectors savedUserSectors = userSectorsRepository.save(newUserSectors);
                sectorsFromDb.forEach(sector -> {
                    List<UserSectors> userSectors = sector.getUserSectors();
                    userSectors.add(savedUserSectors);
                    sector.setUserSectors(userSectors);
                    sectorsRepository.save(sector);
                });
                return savedUserSectors;
            }
        }
        return null;
    }

    /**
     * Clears an existing UserSectors list of Sectors before making any changes to the list.
     *
     * @param userSectors
     * @return
     */
    private UserSectors clearSectors(UserSectors userSectors) {
        if (!userSectors.getSectors().isEmpty()) {
            userSectors.getSectors().forEach(sector -> {
                List<UserSectors> userSectorsList = sector.getUserSectors();
                userSectorsList.remove(userSectors);
                sector.setUserSectors(userSectorsList);
                sectorsRepository.save(sector);
            });
        }
        return userSectors;
    }

    /**
     * To check if userSectorsDTO is any different from db version of it.
     * @param userSectorsFromDb
     * @param userSectorsDTO
     * @return
     */
    private boolean isChanged(UserSectors userSectorsFromDb, UserSectorsDTO userSectorsDTO) {
        boolean changed = !userSectorsFromDb.getName().equals(userSectorsDTO.getName());
        if (userSectorsFromDb.isAgreeToTerms() != userSectorsDTO.isAgreeToTerms()) changed = true;
//        only need to compare if the ids match
        if (!userSectorsFromDb.getSectors().stream().map(Sector::getId).toList().equals(
                userSectorsDTO.getSectors().stream().map(sectorDTO ->
                        Long.parseLong(sectorDTO.getId())).toList())
        ) changed = true;

        return changed;
    }
}
