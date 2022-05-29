package com.wefarm.modules.farm.web;

import com.wefarm.modules.account.domain.Account;
import com.wefarm.modules.common.web.BaseApiController;
import com.wefarm.modules.farm.application.FarmService;
import com.wefarm.modules.farm.application.request.FarmCreateRequest;
import com.wefarm.modules.farm.application.request.FarmSearchRequest;
import com.wefarm.modules.farm.application.request.FarmUpdateRequest;
import com.wefarm.modules.farm.domain.Farm;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class FarmController extends BaseApiController {

    private final FarmService farmService;
    private final ModelMapper modelMapper;

    @GetMapping("/farms")
    public Page<Farm> list(FarmSearchRequest farmSearchRequest) {
        return farmService.list(farmSearchRequest);
    }

    @PostMapping("/farms")
    public void create(@RequestBody FarmCreateRequest farmCreateRequest) {
        farmService.create(modelMapper.map(farmCreateRequest, Farm.class));
    }

    @PatchMapping("/farms/{id}")
    public void update(@PathVariable Long id,
        @RequestBody FarmUpdateRequest updateRequest) {
        updateRequest.setId(id);
        farmService.update(updateRequest);
    }

    @GetMapping("/farms/{id}")
    public Farm findOne(@PathVariable Long id) {
        return farmService.findOne(id);
    }

    @PostMapping("/farms/{id}/add")
    public ResponseEntity addFarm(@PathVariable Long id, Account account) {
        Farm one = farmService.findOne(id);
//        one.addAccount(account);
        return ResponseEntity.ok().build();
    }
}
