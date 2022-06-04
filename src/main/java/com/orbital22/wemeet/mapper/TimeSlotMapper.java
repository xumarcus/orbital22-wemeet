package com.orbital22.wemeet.mapper;

import com.orbital22.wemeet.dto.TimeSlotDto;
import com.orbital22.wemeet.model.TimeSlot;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TimeSlotMapper {
  TimeSlotMapper INSTANCE = Mappers.getMapper(TimeSlotMapper.class);

  TimeSlotDto timeSlotToTimeSlotDto(TimeSlot timeSlot);

  TimeSlot timeSlotDtoToTimeSlot(TimeSlotDto dto);
}
