package com.martintonts.controller;

import com.martintonts.dto.UserSectorsDTO;
import com.martintonts.model.UserSectors;
import com.martintonts.service.UserSectorsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-sectors")
@RequiredArgsConstructor
public class UserSectorsController {
    private final UserSectorsService userSectorsService;

    @PostMapping
    public UserSectors save(@RequestBody UserSectorsDTO userSectorsDTO) {
        return userSectorsService.saveUserSectors(userSectorsDTO);
    }
}
