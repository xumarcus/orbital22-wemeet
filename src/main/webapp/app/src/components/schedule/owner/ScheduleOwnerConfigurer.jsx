import { FormLabel, Select, Slider } from '@mui/material'
import MenuItem from '@mui/material/MenuItem'
import * as React from 'react'
import { useState } from 'react'
import { CONFIG, TEXT } from '../../../core/const'
import FormControl from '@mui/material/FormControl'
import ajax from '../../../core/ajax'

const ScheduleOwnerConfigurer = ({
  rosterPlan,
  eventDuration,
  setEventDuration
}) => {
  const [minMax, setMinMax] = useState(
    [rosterPlan.minAllocationCount, rosterPlan.maxAllocationCount])

  const handleDurationChange = ({ target: { value } }) => setEventDuration(
    value)
  const handleMinMaxChange = (e, newMinMax) => setMinMax(newMinMax)
  const handleMinMaxChangeCommitted = async (e, newMinMax) => {
    const [minAllocationCount, maxAllocationCount] = newMinMax
    await ajax('PATCH', { minAllocationCount, maxAllocationCount })(
      rosterPlan._links.self.href)
  }

  const marks = [
    {
      value: CONFIG.SCHEDULE.OWNER.CONFIGURER.NUMBER_OF_SLOTS.MIN,
      label: CONFIG.SCHEDULE.OWNER.CONFIGURER.NUMBER_OF_SLOTS.MIN.toString()
    },
    {
      value: CONFIG.SCHEDULE.OWNER.CONFIGURER.NUMBER_OF_SLOTS.MAX,
      label: CONFIG.SCHEDULE.OWNER.CONFIGURER.NUMBER_OF_SLOTS.MAX.toString()
    }
  ]

  return (
    <>
      <FormControl fullWidth sx={{ my: 2 }}>
        <FormLabel>{TEXT.SCHEDULE.OWNER.CONFIGURER.EVENT_DURATION}</FormLabel>
        <Select
          id='event-duration'
          onChange={handleDurationChange}
          value={eventDuration}
        >
          {TEXT.SCHEDULE.OWNER.CONFIGURER.DURATIONS.map(
            ({ value, label }) => (
              <MenuItem
                key={value}
                value={value}
              >
                {label}
              </MenuItem>
            ))}
        </Select>
      </FormControl>

      <FormControl fullWidth sx={{ my: 2 }}>
        <FormLabel>{TEXT.SCHEDULE.OWNER.CONFIGURER.NUMBER_OF_SLOTS}</FormLabel>
        <Slider
          value={minMax}
          onChange={handleMinMaxChange}
          onChangeCommitted={handleMinMaxChangeCommitted}
          marks={marks}
          min={CONFIG.SCHEDULE.OWNER.CONFIGURER.NUMBER_OF_SLOTS.MIN}
          max={CONFIG.SCHEDULE.OWNER.CONFIGURER.NUMBER_OF_SLOTS.MAX}
          valueLabelDisplay='auto'
        />
      </FormControl>
    </>
  )
}

export default ScheduleOwnerConfigurer
