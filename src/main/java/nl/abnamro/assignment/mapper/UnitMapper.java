package nl.abnamro.assignment.mapper;

import nl.abnamro.assignment.dto.UnitDto;
import nl.abnamro.assignment.dto.Units;
import nl.abnamro.assignment.model.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, injectionStrategy = CONSTRUCTOR)
public interface UnitMapper {
    @Mapping(target = "id", ignore = true)
    Unit convert(UnitDto unit);

    UnitDto convert(Unit unit);

    List<UnitDto> convert(List<Unit> units);

    default Units items(List<Unit> units) {
        return new Units(convert(units));
    }

}
