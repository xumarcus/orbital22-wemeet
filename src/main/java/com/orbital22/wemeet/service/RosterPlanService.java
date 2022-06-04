package com.orbital22.wemeet.service;

import com.orbital22.wemeet.dto.RosterPlanCreateRequest;
import com.orbital22.wemeet.model.RosterPlan;
import com.orbital22.wemeet.model.TimeSlot;
import com.orbital22.wemeet.model.User;
import com.orbital22.wemeet.repository.RosterPlanRepository;
import com.orbital22.wemeet.repository.TimeSlotRepository;
import com.orbital22.wemeet.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class RosterPlanService {
  private RosterPlanRepository rosterPlanRepository;
  private TimeSlotRepository timeSlotRepository;
  private UserRepository userRepository;

  public RosterPlan create(RosterPlanCreateRequest request, int id) {
    User owner = userRepository.findById(id).orElseThrow();
    Set<TimeSlot> timeSlots = timeSlotRepository.saveAll(); // FIXME
    RosterPlan rosterPlan = RosterPlan.builder().owner(owner).title(request.getTitle()).build();
    return rosterPlanRepository.save(rosterPlan);
  }
}
