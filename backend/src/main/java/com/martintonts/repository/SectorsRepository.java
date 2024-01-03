package com.martintonts.repository;

import com.martintonts.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorsRepository extends JpaRepository<Sector, Long> {
    Sector findSectorByVal(String val);
}
