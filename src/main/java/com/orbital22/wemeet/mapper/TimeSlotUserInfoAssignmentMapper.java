package com.orbital22.wemeet.mapper;

import com.orbital22.wemeet.model.TimeSlotUserInfo;
import com.orbital22.wemeet.solver.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TimeSlotUserInfoAssignmentMapper {
  TimeSlotUserInfoAssignmentMapper INSTANCE =
      Mappers.getMapper(TimeSlotUserInfoAssignmentMapper.class);

  @Mapping(target = "considered", ignore = true)
  Assignment timeSlotUserInfoToAssignment(TimeSlotUserInfo timeSlotUserInfo);

  @Mapping(source = "considered", target = "picked")
  TimeSlotUserInfo assignmentToTimeSlotUserInfo(Assignment assignment);
}
