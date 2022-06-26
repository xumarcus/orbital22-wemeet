import * as React from 'react'
import { useContext, useState } from 'react'
import { useParams } from 'react-router-dom'
import ErrorFallback from '../components/ErrorFallback'
import { ErrorBoundary } from 'react-error-boundary'
import ScheduleEdit from '../components/ScheduleEdit'
import useSWR from 'swr'
import {
    CircularProgress,
    Divider,
    FormHelperText,
    Select,
    TextField,
    ToggleButtonGroup,
} from '@mui/material'
import ajax from '../core/ajax'
import InvitationGrid from '../components/InvitationGrid'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import { API, ERROR_MESSAGES } from '../core/const'
import AppContext from '../core/AppContext'
import FormControl from '@mui/material/FormControl'
import MenuItem from '@mui/material/MenuItem'
import QuickMenuEventSettings from '../components/QuickMenuEventSettings'
import QuickMenuBulkAdd from '../components/QuickMenuBulkAdd'
import QuickMenuBulkEdit from '../components/QuickMenuBulkEdit'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import { ToggleButton } from '@mui/lab'

/* TODO fetch rosterPlan from BE (cached request) with swr
    Then fetch time slots and infos... then everything
    Use DM to connect component to CRUD API
    Include Grid of users invited
    [Optimization] can use projection if too many requests
*/

const MeetingEdit = () => {
  const { context } = useContext(AppContext)
  const params = useParams()

  return (
    <ErrorBoundary FallbackComponent={ErrorFallback} resetKeys={[params, context]}>
      <Inner meetingId={params.meetingId} />
    </ErrorBoundary>
  )
}

const Inner = ({ meetingId }) => {
  const menuPages = [
    ['configurations', 'Meeting Configurations'],
    ['add', 'Bulk Add Events'],
    ['edit', 'Bulk Edit Events']
  ]
  const [currMenu, setCurrMenu] = useState(null)
  const [meetingTitle, setMeetingTitle] = useState('test')
  const [mode, setMode] = useState('set')

  if (meetingId === undefined) {
    throw new Error(ERROR_MESSAGES.INVALID_URL)
  }

  const { data, error } = useSWR(API.ROSTER_PLAN_ID(meetingId), ajax('GET'))
  if (error) throw new Error(error)
  if (!data) return <CircularProgress />

  const handleMenuChange = e => {
    setCurrMenu(e.target.value)
  }

  const handleMeetingTitleChange = (e) => {
    setMeetingTitle(e.target.value)
  }

  const handleModeChange = (e) => {
    // TODO: - handle changing of meeting mode
  }

  const handleGenerate = (e) => {
    // TODO: - handle generating of roster
  }

  return (
    <ErrorBoundary FallbackComponent={ErrorFallback} resetKeys={[data]}>
      {/* <PageTitle pageTitle={data?.title} /> */}
      <Box sx={{
        display: 'flex',
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center'
      }}
      >
        <Typography variant='h4' sx={{ mx: 2 }}>
          Meeting Title:
        </Typography>
        <TextField
          id='outlined-basic'
          defaultValue={meetingTitle}
          variant='outlined'
          onChange={handleMeetingTitleChange}
        />
        <ToggleButtonGroup
          color='primary'
          value={mode}
          exclusive
          onChange={handleModeChange}
        >
          <ToggleButton value='set'>Set Available Slots</ToggleButton>
          <ToggleButton value='review'>Review Time Slots</ToggleButton>
        </ToggleButtonGroup>
      </Box>
      <Grid container spacing={5}>
        <Grid item xs={12} lg={8}>
          <Box>
            <Box sx={{ flexDirection: 'row' }}>
              <Typography variant='h5' sx={{ my: 2 }}>
                Quick Menu
              </Typography>
              <FormControl sx={{ mx: 1 }}>
                <Select
                  value={currMenu}
                  onChange={handleMenuChange}
                >
                  {menuPages.map(([value, description]) => <MenuItem key={value} value={value}>{description}</MenuItem>)}
                </Select>
                <FormHelperText>Without label</FormHelperText>
              </FormControl>
            </Box>
            {currMenu === 'configurations' && <QuickMenuEventSettings />}
            {currMenu === 'add' && <QuickMenuBulkAdd />}
            {currMenu === 'edit' && <QuickMenuBulkEdit />}
          </Box>
          <Divider variant='middle' />
          <Box>
            <Typography variant='h5' sx={{ my: 2 }}>
              Schedule
            </Typography>
            <ScheduleEdit rosterPlan={data} />
          </Box>
        </Grid>
        <Grid item xs={12} lg={4}>
          <Typography variant='h5' sx={{ my: 2 }}>
            Invitations
          </Typography>
          <InvitationGrid rosterPlan={data} invitationGrid={{ readonly: false }} />
          <Button variant='contained' color='success' onClick={handleGenerate}>Generate Roster</Button>
        </Grid>
      </Grid>
    </ErrorBoundary>
  )
}

export default MeetingEdit
