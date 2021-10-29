package me.kvq.hospitaltask.controller;

import lombok.RequiredArgsConstructor;
import me.kvq.hospitaltask.dto.TariffDto;
import me.kvq.hospitaltask.service.TariffService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tariff")
@RequiredArgsConstructor
public class TariffController {
    private final TariffService tariffService;

    @PostMapping("/update")
    @PreAuthorize("hasAuthority(\"UPDATE_TARIFF\")")
    public TariffDto update(TariffDto dto) {
        return tariffService.updateTariff(dto);
    }

    @DeleteMapping("/delete/{name}")
    @PreAuthorize("hasAuthority(\"DELETE_TARIFF\")")
    public void delete(@PathVariable String name) {
        tariffService.deleteTariff(name);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority(\"SEE_ALL_TARIFFS\")")
    public List<TariffDto> list() {
        return tariffService.getAllTariffs();
    }

}
