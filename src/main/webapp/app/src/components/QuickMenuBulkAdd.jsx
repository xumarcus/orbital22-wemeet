import * as React from 'react'
import Typography from '@mui/material/Typography'
import { DatePickerComponent } from '@syncfusion/ej2-react-calendars'
import FormControl from '@mui/material/FormControl'
import { Select } from '@mui/material'
import InputLabel from '@mui/material/InputLabel'
import MenuItem from '@mui/material/MenuItem'
import Button from '@mui/material/Button'
import { ENUMS } from '../core/const'

const QuickMenuBulkAdd = () => {
  const [frequency, setFrequency] = React.useState(ENUMS.QUICK_MENU_BULK_ADD.FREQUENCY.DAILY)
  const [startDate, setStartDate] = React.useState(new Date())
  const [endDate, setEndDate] = React.useState(new Date())

  const handleFrequencyChange = (e) => {
    setFrequency(e.target.value)
  }

  const handleAddToSlots = (e) => {

  }

  const handleClearSelection = (e) => {
    setFrequency('daily')
  }

  const handleStartDateChange = (e) => {
    setStartDate(e.value)
  }

  const handleEndDateChange = (e) => {
    setEndDate(e.value)
  }

  return (
    <>
      <Typography variant='h6'>
        Bulk Add Available Slots
      </Typography>
      <DatePickerComponent value={startDate} onChange={handleStartDateChange} id='startDate' placeholder='Enter Start Date' />
      <DatePickerComponent value={endDate} onChange={handleEndDateChange} id='endDate' placeholder='Enter End Date' />
      <FormControl fullWidth>
        <InputLabel id='frequency-label'>Frequency</InputLabel>
        <Select
          labelId='frequency-label'
          id='frequency'
          value={frequency}
          label='Frequency'
          onChange={handleFrequencyChange}
        >
          <MenuItem value='daily'>Daily</MenuItem>
          <MenuItem value='weekdays'>Weekdays Only</MenuItem>
          <MenuItem value='weekends'>Weekends Only</MenuItem>
        </Select>
      </FormControl>
      <Button variant='contained' color='success' onClick={handleAddToSlots}>
        Add to Available Slots
      </Button>
      <Button variant='outlined' onClick={handleClearSelection}>
        Reset Selection
      </Button>
    </>
  )
}

export default QuickMenuBulkAdd
