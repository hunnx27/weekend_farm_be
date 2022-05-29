package com.wefarm.modules.farm.application;

import com.wefarm.modules.farm.application.request.FarmSearchRequest;
import com.wefarm.modules.farm.application.request.FarmUpdateRequest;
import com.wefarm.modules.farm.domain.Farm;
import com.wefarm.modules.farm.infra.FarmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FarmService {

    private final FarmRepository farmRepository;

    public Page<Farm> list(FarmSearchRequest farmSearchRequest) {
        return farmRepository.list(farmSearchRequest);
    }

    public void create(Farm farm) {
        farmRepository.save(farm);
    }

    public void update(FarmUpdateRequest updateRequest) {
        farmRepository.update(updateRequest);
    }

    public Farm findOne(Long id) {
        return farmRepository.findById(id)
            .orElseThrow();
    }
}
