package com.wefarm.modules.farm.infra;


import com.wefarm.modules.farm.application.request.FarmSearchRequest;
import com.wefarm.modules.farm.application.request.FarmUpdateRequest;
import com.wefarm.modules.farm.domain.Farm;
import org.springframework.data.domain.PageImpl;

public interface FarmRepositoryExtension {

    PageImpl<Farm> list(FarmSearchRequest farmSearchRequest);

    void update(FarmUpdateRequest updateRequest);
}
