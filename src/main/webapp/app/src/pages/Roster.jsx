import Typography from '@mui/material/Typography'
import * as React from 'react'
import CenterWrapper from '../components/CenterWrapper'
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider'
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns'
import { DatePicker } from '@mui/x-date-pickers/DatePicker'
import TextField from '@mui/material/TextField'
import FormControl from '@mui/material/FormControl'
import InputLabel from '@mui/material/InputLabel'
import Select from '@mui/material/Select'
import MenuItem from '@mui/material/MenuItem'
import Button from '@mui/material/Button'
import Box from '@mui/material/Box'

const Roster = () => {
  const [startDate, setStartDate] = React.useState(null)
  const [endDate, setEndDate] = React.useState(null)

  const [frequency, setFrequency] = React.useState('')

  const handleChange = (event) => {
    setFrequency(event.target.value)
  }

  const handleSubmit = () => {
    console.log(startDate, endDate, frequency)
  }

  const events = [{ title: 'today\'s event', date: new Date() }]

  return (
    <>
      <CenterWrapper>
        <Typography variant="h3" component="div" textAlign="center">
          Roster
        </Typography>
        <Box sx={{ minWidth: 120 }}>
          <TextField id="outlined-basic" label="List of Names"
                     variant="outlined"/>
          <br/>
          <LocalizationProvider dateAdapter={AdapterDateFns}>
            <DatePicker
              label="Start Date"
              value={startDate}
              onChange={(newValue) => {
                setStartDate(newValue)
              }}
              renderInput={(params) => <TextField {...params} />}
            />

            <DatePicker
              label="End Date"
              value={endDate}
              onChange={(newValue) => {
                setEndDate(newValue)
              }}
              renderInput={(params) => <TextField {...params} />}
            />
          </LocalizationProvider>
          <FormControl fullWidth>

            <InputLabel id="demo-simple-select-label">Frequency</InputLabel>
            <Select
              labelId="demo-simple-select-label"
              id="demo-simple-select"
              value={frequency}
              label="Frequency"
              onChange={handleChange}
            >
              <MenuItem value={1}>Daily</MenuItem>
              <MenuItem value={2}>Weekly</MenuItem>
              <MenuItem value={3}>Monthly</MenuItem>
              <MenuItem value={4}>Weekdays</MenuItem>
              <MenuItem value={5}>Weekends</MenuItem>
            </Select>
          </FormControl>
          <Button variant="contained" onClick={handleSubmit}>Submit</Button>
        </Box>
      </CenterWrapper>
    </>
  )
}

export default Roster
