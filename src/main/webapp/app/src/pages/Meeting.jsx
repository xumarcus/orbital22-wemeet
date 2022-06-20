import * as React from 'react'
import PageTitle from '../components/PageTitle'
import { useParams } from 'react-router-dom'
import { DataManager, Query } from '@syncfusion/ej2-data'
import RestAdaptor from '../core/RestAdaptor'
import ErrorFallback from '../components/ErrorFallback'
import { ErrorBoundary } from 'react-error-boundary'
import CustomScheduleComponent from '../components/CustomScheduleComponent'
import useSWR from 'swr'
import { CircularProgress } from '@mui/material'
import ajax from '../core/ajax'

const Meeting = () => {
  const params = useParams()
  const meetingId = parseInt(params.meetingId)
  const url = `/api/rosterPlan/${meetingId}`

  /* TODO fetch rosterPlan from BE (cached request) with swr
      Then fetch time slots and infos... then everything
      Use DM to connect component to CRUD API
      Include Grid of users invited
      [Optimization] can use projection if too many requests
  */

  const { data, error } = useSWR(url, ajax('GET'))
  if (error) return <ErrorFallback />
  if (!data) return <CircularProgress />

  return (
    <ErrorBoundary FallbackComponent={ErrorFallback} resetKeys={[data]}>
      <PageTitle pageTitle={data?.title} />
      <CustomScheduleComponent rosterPlan={data} />
    </ErrorBoundary>
  )
}

export default Meeting
