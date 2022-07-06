import * as React from 'react'
import { useContext, useState } from 'react'
import { useParams } from 'react-router-dom'
import ErrorFallback from '../components/core/ErrorFallback'
import { ErrorBoundary } from 'react-error-boundary'
import ScheduleOwner from '../components/schedule/owner/ScheduleOwner'
import useSWR from 'swr'
import {
  CircularProgress,
  Divider,
  TextField,
  ToggleButton,
  ToggleButtonGroup,
} from '@mui/material'
import ajax from '../core/ajax'
import InvitationGrid from '../components/schedule/InvitationGrid'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import { API, ERROR_MESSAGES } from '../core/const'
import AppContext from '../core/AppContext'
import EventSettingsTab
  from '../components/schedule/owner/quick_menu/QuickMenuEventSettings'
import BulkAddTab
  from '../components/schedule/owner/quick_menu/QuickMenuBulkAdd'
import BulkEditTab
  from '../components/schedule/owner/quick_menu/QuickMenuBulkEdit'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import { TabContext, TabList, TabPanel } from '@mui/lab'
import Tab from '@mui/material/Tab'
import SolutionGrid from '../components/schedule/owner/SolutionGrid'

const MeetingEdit = () => {
  const { context } = useContext(AppContext)
  const params = useParams()

  return (
    <ErrorBoundary
      FallbackComponent={ErrorFallback}
      resetKeys={[params, context]}
    >
      <Inner meetingId={params.meetingId} />
    </ErrorBoundary>
  )
}

const Inner = ({ meetingId }) => {
  if (meetingId === undefined) {
    throw new Error(ERROR_MESSAGES.INVALID_URL)
  }

  const { data, error } = useSWR(API.ROSTER_PLAN_ID(meetingId), ajax('GET'))
  if (error) throw new Error(error)
  if (!data) return <CircularProgress />
  return <InnerInner rosterPlan={data} />
}

const InnerInner = ({ rosterPlan }) => {
  /*
  const menuPages = [
    ['configurations', 'Meeting Configurations'],
    ['add', 'Bulk Add Events'],
    ['edit', 'Bulk Edit Events']
  ]
  const [currMenu, setCurrMenu] = useState('configurations')
   */
  const [meetingTitle, setMeetingTitle] = useState(rosterPlan?.title)
  const [isTitleUpdated, setIsTitleUpdated] = useState(true)
  const [mode, setMode] = useState('set')
  const [currTab, setCurrTab] = useState('1')

  const handleMeetingTitleChange = (e) => {
    setMeetingTitle(e.target.value)
    setIsTitleUpdated(false)
  }

  const handleSaveMeetingTitle = async () => {
    // TODO: - handle saving of meeting title
    await ajax('PATCH', { title: meetingTitle })(`${API.ROSTER_PLAN}/${rosterPlan.id}`)
    setIsTitleUpdated(true)
  }

  const handleModeChange = (e) => {
    setMode(e.target.value)
    // TODO: - handle changing of meeting mode
  }

  const handleTabChange = (event, newValue) => {
    setCurrTab(newValue)
  }

  return (
    <ErrorBoundary FallbackComponent={ErrorFallback} resetKeys={[rosterPlan]}>
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
          value={meetingTitle}
          variant='outlined'
          onChange={handleMeetingTitleChange}
        />
        {!isTitleUpdated &&
          <Button variant='contained' onClick={handleSaveMeetingTitle}>
            Update
          </Button>}
        <ToggleButtonGroup
          color='primary'
          value={mode}
          exclusive
          onChange={handleModeChange}
          sx={{ ml: 3 }}
        >
          <ToggleButton value='set'>Set Available Slots</ToggleButton>
          <ToggleButton value='review'>Review Time Slots</ToggleButton>
        </ToggleButtonGroup>
      </Box>
      <Grid container spacing={5}>
        <Grid item xs={12} lg={8}>
          <Box>
            <Typography variant='h5' sx={{ my: 2 }}>
              Schedule
            </Typography>
            <ScheduleOwner rosterPlan={rosterPlan} />
          </Box>
        </Grid>
        <Grid item xs={12} lg={4}>
          <Box>
            <Typography variant='h5' sx={{ my: 2 }}>
              Menu
            </Typography>
            <Box sx={{ width: '100%', typography: 'body1' }}>
              <TabContext value={currTab}>
                <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                  <TabList onChange={handleTabChange} aria-label='menu tabs'>
                    <Tab label='Configurations' value='1' />
                    <Tab label='Bulk Add' value='2' />
                    <Tab label='Bulk Edit' value='3' />
                  </TabList>
                </Box>
                <TabPanel value='1'>
                  <EventSettingsTab />
                </TabPanel>
                <TabPanel value='2'>
                  <BulkAddTab />
                </TabPanel>
                <TabPanel value='3'>
                  <BulkEditTab />
                </TabPanel>
              </TabContext>
            </Box>
          </Box>
          <Divider variant='middle' />
          <Typography variant='h5' sx={{ my: 2 }}>
            Invitations
          </Typography>
          <InvitationGrid rosterPlan={rosterPlan} />
          <SolutionGrid rosterPlan={rosterPlan} />
        </Grid>
      </Grid>
    </ErrorBoundary>
  )
}

export default MeetingEdit
