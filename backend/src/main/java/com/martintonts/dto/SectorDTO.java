package com.martintonts.dto;

import com.martintonts.model.UserSectors;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class SectorDTO {
    private String id;

    private String val;

    private String name;

    private int level;

    private int pos;
}
