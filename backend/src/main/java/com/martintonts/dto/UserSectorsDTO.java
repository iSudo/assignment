package com.martintonts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class UserSectorsDTO {
    private String id;

    private String name;

    private List<SectorDTO> sectors;

    private boolean agreeToTerms;
}
