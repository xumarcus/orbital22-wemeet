package com.orbital22.wemeet.mapper;

import com.orbital22.wemeet.dto.RosterPlanDto;
import com.orbital22.wemeet.model.RosterPlan;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RosterPlanMapper {
  RosterPlanMapper INSTANCE = Mappers.getMapper(RosterPlanMapper.class);

  RosterPlanDto rosterPlanToRosterPlanDto(RosterPlan rosterPlan);

  RosterPlan rosterPlanDtoToRosterPlan(RosterPlanDto dto);
}
