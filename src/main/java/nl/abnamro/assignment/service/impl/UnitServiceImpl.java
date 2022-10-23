package nl.abnamro.assignment.service.impl;

import lombok.RequiredArgsConstructor;
import nl.abnamro.assignment.dto.UnitDto;
import nl.abnamro.assignment.dto.Units;
import nl.abnamro.assignment.exception.NotFoundException;
import nl.abnamro.assignment.mapper.UnitMapper;
import nl.abnamro.assignment.model.Unit;
import nl.abnamro.assignment.repository.UnitRepository;
import nl.abnamro.assignment.service.UnitService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    private final UnitMapper unitMapper;

    @Override
    public void saveUnit(UnitDto unitDto) {
        Unit unit = unitMapper.convert(unitDto);
        unit.setName(unit.getName().toLowerCase());
        unitRepository.save(unit);
    }

    @Override
    public Unit getUnitEntityByName(String name) {
        return unitRepository.findFirstByNameIgnoreCase(name)
            .orElseThrow(() -> new NotFoundException("Could not find unit with name: " + name));
    }

    @Override
    public UnitDto getUnitByName(String name) {
        return unitMapper.convert(getUnitEntityByName(name));
    }

    @Override
    public Units getAllUnits() {
        return unitMapper.items(unitRepository.findAll());
    }
}
