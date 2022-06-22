import * as React from 'react'
import PageTitle from '../components/PageTitle'
import { useParams } from 'react-router-dom'
import ErrorFallback from '../components/ErrorFallback'
import { ErrorBoundary } from 'react-error-boundary'
import CustomScheduleComponent from '../components/CustomScheduleComponent'
import useSWR from 'swr'
import { CircularProgress, Divider, FormHelperText, FormLabel, Select } from '@mui/material'
import ajax from '../core/ajax'
import InvitationGrid from '../components/InvitationGrid'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import Box from '@mui/material/Box'
import InputLabel from '@mui/material/InputLabel'
import FormControl from '@mui/material/FormControl'
import { useState } from 'react'
import MenuItem from '@mui/material/MenuItem'
import Button from '@mui/material/Button'
import QuickMenuEventSettings from '../components/QuickMenuEventSettings'
import QuickMenuBulkAdd from '../components/QuickMenuBulkAdd'
import QuickMenuBulkEdit from '../components/QuickMenuBulkEdit'

const Meeting = () => {
  const params = useParams()
  return <MeetingInner url={`/api/rosterPlan/${params.meetingId}`} />
}

const MeetingInner = ({ url }) => {
  /* TODO fetch rosterPlan from BE (cached request) with swr
        Then fetch time slots and infos... then everything
        Use DM to connect component to CRUD API
        Include Grid of users invited
        [Optimization] can use projection if too many requests
    */
  const [currMenu, setCurrMenu] = useState('EventSettings')
  const menuPages = {
    configurations: 'Meeting Configurations',
    add: 'Bulk Add Events',
    edit: 'Bulk Edit Events'
  }

  const handleMenuChange = e => {
    setCurrMenu(e.target.value)
  }

  const { data, error } = useSWR(url, ajax('GET'))
  if (error) return <ErrorFallback />
  if (!data) return <CircularProgress />

  return (
    <>
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
                    <MenuItem value='configurations'>{menuPages.configurations}</MenuItem>
                    <MenuItem value='add'>{menuPages.add}</MenuItem>
                    <MenuItem value='edit'>{menuPages.edit}</MenuItem>
                  </Select>
                  <FormHelperText>Without label</FormHelperText>
                </FormControl>
              </Box>
              {(currMenu === 'configurations' && QuickMenuEventSettings) ||
                            (currMenu === 'add' && QuickMenuBulkAdd) ||
                            (currMenu === 'edit' && QuickMenuBulkEdit)}
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
            <InvitationGrid rosterPlan={data} />
          </Grid>
        </Grid>
      </ErrorBoundary>
    </>
  )
}

export default Meeting
