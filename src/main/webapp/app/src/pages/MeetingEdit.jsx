import * as React from 'react'
import { useContext, useState } from 'react'
import PageTitle from '../components/PageTitle'
import { useParams } from 'react-router-dom'
import ErrorFallback from '../components/ErrorFallback'
import { ErrorBoundary } from 'react-error-boundary'
import CustomScheduleComponent from '../components/CustomScheduleComponent'
import useSWR from 'swr'
import {
  CircularProgress,
  Divider,
  FormHelperText,
  Select,
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

  if (meetingId === undefined) {
    throw new Error(ERROR_MESSAGES.INVALID_URL)
  }

  const { data, error } = useSWR(API.ROSTER_PLAN_ID(meetingId), ajax('GET'))
  if (error) throw new Error(error)
  if (!data) return <CircularProgress />

  const handleMenuChange = e => {
    setCurrMenu(e.target.value)
  }

  return (
    <ErrorBoundary FallbackComponent={ErrorFallback} resetKeys={[data]}>
      <PageTitle pageTitle={data?.title} />
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
            <CustomScheduleComponent rosterPlan={data} />
          </Box>
        </Grid>
        <Grid item xs={12} lg={4}>
          <Typography variant='h5' sx={{ my: 2 }}>
            Invitations
          </Typography>
          <InvitationGrid rosterPlan={data} invitationGrid={{ readonly: false }}/>
        </Grid>
      </Grid>
    </ErrorBoundary>
  )
}

export default MeetingEdit
