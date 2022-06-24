import FormControl from '@mui/material/FormControl'
import { FormLabel, Select } from '@mui/material'
import MenuItem from '@mui/material/MenuItem'
import Button from '@mui/material/Button'
import * as React from 'react'
import { useState } from 'react'

const QuickMenuEventSettings = () => {
  const [eventDuration, setEventDuration] = useState('60')

  const handleDurationChange = e => {
    setEventDuration(e.target.value)
  }
  return (
    <>
      <FormControl fullWidth>
        <FormLabel>Event Duration</FormLabel>
        {/* <InputLabel id="demo-simple-select-label">Event Duration</InputLabel> */}
        <Select
          labelId='event-duration'
          id='event-duration'
          value={eventDuration}
          label='Event Duration'
          onChange={handleDurationChange}
          sx={{ my: 2 }}
        >
          <MenuItem value={15}>15 Minutes</MenuItem>
          <MenuItem value={30}>30 Minutes</MenuItem>
          <MenuItem value={45}>45 Minutes</MenuItem>
          <MenuItem value={60}>1 Hour</MenuItem>
        </Select>
      </FormControl>
      <Button variant='contained' sx={{ my: 2 }}>Update</Button>
    </>
  )
}

export default QuickMenuEventSettings
