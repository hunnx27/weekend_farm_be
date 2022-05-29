package com.wefarm.modules.farm.infra;


import com.wefarm.modules.farm.domain.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm, Long>,
        FarmRepositoryExtension {

}
