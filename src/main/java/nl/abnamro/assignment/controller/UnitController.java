package nl.abnamro.assignment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nl.abnamro.assignment.dto.UnitDto;
import nl.abnamro.assignment.dto.Units;
import nl.abnamro.assignment.service.UnitService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Tag(name = "Units",
    description = "API for units")
@RestController
@RequestMapping("/unit")
@RequiredArgsConstructor
public class UnitController {
    private final UnitService unitService;

    @Operation(summary = "Create unit")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void saveUnit(@Valid @RequestBody UnitDto unit) {
        unitService.saveUnit(unit);
    }

    @Operation(summary = "Get the unit by name. Names of units are unique")
    @GetMapping("/{name}")
    UnitDto getUnitByName(@PathVariable String name) {
        return unitService.getUnitByName(name);
    }

    @Operation(summary = "Get all units")
    @GetMapping
    Units getAllUnits() {
        return unitService.getAllUnits();
    }
}
