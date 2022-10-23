package nl.abnamro.assignment.service;

import nl.abnamro.assignment.dto.UnitDto;
import nl.abnamro.assignment.dto.Units;
import nl.abnamro.assignment.model.Unit;

public interface UnitService {

    void saveUnit(UnitDto unit);

    Unit getUnitEntityByName(String name);

    UnitDto getUnitByName(String name);

    Units getAllUnits();

}
