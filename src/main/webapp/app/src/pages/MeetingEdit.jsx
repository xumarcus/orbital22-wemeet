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
  TextField,
  ToggleButton,
  ToggleButtonGroup,
} from '@mui/material'
import ajax from '../core/ajax'
import InvitationGrid from '../components/InvitationGrid'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import { API, ERROR_MESSAGES } from '../core/const'
import AppContext from '../core/AppContext'
import EventSettingsTab from '../components/QuickMenuEventSettings'
import BulkAddTab from '../components/QuickMenuBulkAdd'
import BulkEditTab from '../components/QuickMenuBulkEdit'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import { TabContext, TabList, TabPanel } from '@mui/lab'
import Tab from '@mui/material/Tab'

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
    <ErrorBoundary
      FallbackComponent={ErrorFallback}
      resetKeys={[params, context]}
    >
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
  const [currMenu, setCurrMenu] = useState('configurations')
  const [meetingTitle, setMeetingTitle] = useState(data?.title)
  const [isTitleUpdated, setIsTitleUpdated] = useState(true)
  const [mode, setMode] = useState('set')
  const [currTab, setCurrTab] = useState('1')

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
    setIsTitleUpdated(false)
  }

  const handleSaveMeetingTitle = () => {
    // TODO: - handle saving of meeting title
    setIsTitleUpdated(true)
  }

  const handleModeChange = (e) => {
    setMode(e.target.value)
    // TODO: - handle changing of meeting mode
  }

  const handleGenerate = (e) => {
    // TODO: - handle generating of roster
  }

  const handleTabChange = (event, newValue) => {
    setCurrTab(newValue)
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
            <ScheduleEdit rosterPlan={data} />
          </Box>
        </Grid>
        <Grid item xs={12} lg={4}>
          <Box>
            {/* <Box sx={{ */}
            {/*  display: 'flex', */}
            {/*  flexDirection: 'row' */}
            {/* }} */}
            {/* > */}
            {/*  <Typography variant='h5' sx={{ my: 2 }}> */}
            {/*    Menu */}
            {/*  </Typography> */}
            {/*  <FormControl sx={{ mx: 1 }}> */}
            {/*    <Select */}
            {/*      value={currMenu} */}
            {/*      onChange={handleMenuChange} */}
            {/*    > */}
            {/*      {menuPages.map(([value, description]) => <MenuItem key={value} value={value}>{description}</MenuItem>)} */}
            {/*    </Select> */}
            {/*  </FormControl> */}
            {/* </Box> */}
            {/* {currMenu === 'configurations' && <QuickMenuEventSettings />} */}
            {/* {currMenu === 'add' && <QuickMenuBulkAdd />} */}
            {/* {currMenu === 'edit' && <QuickMenuBulkEdit />} */}
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
          <InvitationGrid
            rosterPlan={data}
            invitationGrid={{ readonly: false }}
          />
          <Button variant='contained' color='success' onClick={handleGenerate}>Generate
            Roster
          </Button>
        </Grid>
      </Grid>
    </ErrorBoundary>
  )
}

export default MeetingEdit
