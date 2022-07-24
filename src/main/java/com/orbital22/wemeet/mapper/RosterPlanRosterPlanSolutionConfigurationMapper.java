package com.orbital22.wemeet.mapper;

import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.solver.RosterPlanSolution;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RosterPlanRosterPlanSolutionConfigurationMapper {
  RosterPlanRosterPlanSolutionConfigurationMapper INSTANCE =
      Mappers.getMapper(RosterPlanRosterPlanSolutionConfigurationMapper.class);

  RosterPlanSolution.RosterPlanSolutionConfiguration rosterPlanToRosterPlanSolutionConfiguration(RosterPlan rosterPlan);
}
