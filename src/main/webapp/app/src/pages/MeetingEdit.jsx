import * as React from 'react'
import PageTitle from '../components/PageTitle'
import { useParams } from 'react-router-dom'
import ErrorFallback from '../components/ErrorFallback'
import { ErrorBoundary } from 'react-error-boundary'
import CustomScheduleComponent from '../components/CustomScheduleComponent'
import useSWR from 'swr'
import { CircularProgress } from '@mui/material'
import ajax from '../core/ajax'
import InvitationGrid from '../components/InvitationGrid'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import { API, ERROR_MESSAGES } from '../core/const'

/* TODO fetch rosterPlan from BE (cached request) with swr
    Then fetch time slots and infos... then everything
    Use DM to connect component to CRUD API
    Include Grid of users invited
    [Optimization] can use projection if too many requests
*/

const MeetingEdit = () => {
  const params = useParams()

  return (
    <ErrorBoundary FallbackComponent={ErrorFallback} resetKeys={[params]}>
      <Inner meetingId={params.meetingId}/>
    </ErrorBoundary>
  );
}

const Inner = ({ meetingId }) => {
  if (meetingId === undefined) {
    throw new Error(ERROR_MESSAGES.INVALID_URL);
  }

  const { data, error } = useSWR(API.ROSTER_PLAN_ID(meetingId), ajax('GET'))
  if (error) throw new Error(error);
  if (!data) return <CircularProgress />

  return (
    <ErrorBoundary FallbackComponent={ErrorFallback} resetKeys={[data]}>
      <PageTitle pageTitle={data?.title} />
      <Grid container spacing={5}>
        <Grid item xs={12} lg={8}>
          <Typography variant='h5' sx={{ my: 2 }}>
            Schedule
          </Typography>
          <CustomScheduleComponent rosterPlan={data} />
        </Grid>
        <Grid item xs={12} lg={4}>
          <Typography variant='h5' sx={{ my: 2 }}>
            Invitations
          </Typography>
          <InvitationGrid rosterPlan={data} invitationGrid={{ readonly: false }} />
        </Grid>
      </Grid>
    </ErrorBoundary>
  )
}

export default MeetingEdit
