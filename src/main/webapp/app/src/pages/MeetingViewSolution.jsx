import * as React from 'react'
import { useContext } from 'react'
import { useParams } from 'react-router-dom'
import ErrorFallback from '../components/core/ErrorFallback'
import { ErrorBoundary } from 'react-error-boundary'
import ScheduleOwner from '../components/schedule/owner/ScheduleOwner'
import useSWR from 'swr'
import { CircularProgress, Divider } from '@mui/material'
import ajax from '../core/ajax'
import InvitationGrid from '../components/schedule/InvitationGrid'
import Grid from '@mui/material/Grid'
import Typography from '@mui/material/Typography'
import { API, ERROR_MESSAGES } from '../core/const'
import AppContext from '../core/AppContext'
import Box from '@mui/material/Box'
import PageTitle from '../components/core/PageTitle'
import Button from '@mui/material/Button'

const MeetingViewSolution = () => {
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
  const handlePublish = () => {
    window.alert('To be implemented')
  }

  return (
    <ErrorBoundary FallbackComponent={ErrorFallback} resetKeys={[rosterPlan]}>
      <PageTitle pageTitle={rosterPlan?.title} />
      <Grid container spacing={5}>
        <Grid item xs={12} lg={8}>
          <Box>
            <Typography variant='h5' sx={{ my: 2 }}>
              Schedule
            </Typography>
            <ScheduleOwner rosterPlan={rosterPlan} readOnly />
          </Box>
        </Grid>
        <Grid item xs={12} lg={4}>
          <Button onClick={handlePublish}>Publish</Button>
          <Divider variant='middle' />
          <Typography variant='h5' sx={{ my: 2 }}>
            Invitations
          </Typography>
          <InvitationGrid rosterPlan={rosterPlan} readOnly />
        </Grid>
      </Grid>
    </ErrorBoundary>
  )
}

export default MeetingViewSolution
