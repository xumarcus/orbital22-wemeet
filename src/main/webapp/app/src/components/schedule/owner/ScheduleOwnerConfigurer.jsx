import { FormGroup, Select } from '@mui/material'
import MenuItem from '@mui/material/MenuItem'
import * as React from 'react'
import FormControlLabel from '@mui/material/FormControlLabel'
import { TEXT } from '../../../core/const'
import Typography from '@mui/material/Typography'

const ScheduleOwnerConfigurer = ({ eventDuration, setEventDuration }) => {
  const handleDurationChange = e => {
    setEventDuration(e.target.value)
  }

  return (
    <>
      <Typography variant='h5' sx={{ my: 2 }}>
        Configurations
      </Typography>
      <FormGroup>
        <FormControlLabel
          control={
            <Select
              id='event-duration'
              value={eventDuration}
              onChange={handleDurationChange}
              fullWidth
            >
              {TEXT.SCHEDULE.OWNER.CONFIGURER.DURATIONS.map(
                ({ value, label }) => <MenuItem value={value}>{label}</MenuItem>)}
            </Select>
}
          label={TEXT.EVENT_DURATION}
          labelPlacement='start'
        />
      </FormGroup>
    </>

  )
}

export default ScheduleOwnerConfigurer
