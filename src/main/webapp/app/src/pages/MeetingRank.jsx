import * as React from 'react'
import { useContext } from 'react'
import PageTitle from '../components/PageTitle'
import { useParams } from 'react-router-dom'
import ErrorFallback from '../components/ErrorFallback'
import { ErrorBoundary } from 'react-error-boundary'
import useSWR from 'swr'
import { CircularProgress } from '@mui/material'
import ajax from '../core/ajax'
import InvitationGrid from '../components/InvitationGrid'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import { API, ERROR_MESSAGES } from '../core/const'
import AppContext from '../core/AppContext'
import ScheduleRank from '../components/ScheduleRank'

const MeetingRank = () => {
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

  return (
    <ErrorBoundary FallbackComponent={ErrorFallback} resetKeys={[data]}>
      <PageTitle pageTitle={data?.title} />
      <Grid container spacing={5}>
        <Grid item xs={12} lg={8}>
          <Typography variant='h5' sx={{ my: 2 }}>
            Schedule
          </Typography>
          <ScheduleRank rosterPlan={data} />
        </Grid>
        <Grid item xs={12} lg={4}>
          <Typography variant='h5' sx={{ my: 2 }}>
            Invitations
          </Typography>
          <InvitationGrid
            rosterPlan={data}
            invitationGrid={{ readonly: true }}
          />
        </Grid>
      </Grid>
    </ErrorBoundary>
  )
}

export default MeetingRank
