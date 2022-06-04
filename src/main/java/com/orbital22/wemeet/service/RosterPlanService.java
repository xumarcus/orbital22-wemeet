package com.orbital22.wemeet.service;

import com.orbital22.wemeet.dto.RosterPlanCreateRequest;
import com.orbital22.wemeet.mapper.TimeSlotMapper;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.TimeSlot;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.RosterPlanRepository;
import com.orbital22.wemeet.repository.TimeSlotRepository;
import com.orbital22.wemeet.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.TreeSet;

@Service
@Transactional
@AllArgsConstructor
public class RosterPlanService {
  private RosterPlanRepository rosterPlanRepository;
  private TimeSlotRepository timeSlotRepository;
  private UserRepository userRepository;

  public RosterPlan create(RosterPlanCreateRequest request, int id) {
    User owner = userRepository.findById(id).orElseThrow();
    RosterPlan rosterPlan =
        rosterPlanRepository.save(
            RosterPlan.builder().owner(owner).title(request.getTitle()).build());
    List<TimeSlot> timeSlots =
        timeSlotRepository.saveAll(
            () ->
                request.getTimeSlotDtos().stream()
                    .map(TimeSlotMapper.INSTANCE::timeSlotDtoToTimeSlot)
                    .peek(timeSlot -> timeSlot.setRosterPlan(rosterPlan))
                    .iterator());
    rosterPlan.setTimeSlots(new TreeSet<>(timeSlots));
    return rosterPlan;
  }
}
