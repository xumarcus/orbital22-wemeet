import * as React from 'react'
import PageTitle from '../components/PageTitle'
import {
  Agenda,
  Day,
  Inject,
  Month,
  ScheduleComponent,
  Week,
  Resize,
  DragAndDrop
} from '@syncfusion/ej2-react-schedule'
import { useParams } from 'react-router-dom'
import { DataManager, Query } from '@syncfusion/ej2-data'
import RestAdaptor from '../core/RestAdaptor'
import { useState } from 'react'

const Meeting = () => {
  const params = useParams()
  const meetingId = parseInt(params.meetingId)

  const [title, setTitle] = useState(null)

  /* TODO fetch rosterPlan from BE (cached request) with swr
      Then fetch time slots and infos... then everything
      Use DM to connect component to CRUD API
      Include Grid of users invited
      [Optimization] can use projection if too many requests
  */

  const restAdaptorParams = {
    getUrl: '/api/rosterPlan',
    map: (resp) => resp,
  }

  const rosterPlanDataManager = new DataManager({
    adaptor: new RestAdaptor(restAdaptorParams)
  })

  rosterPlanDataManager
    .executeQuery(new Query().where('id', 'equal', meetingId))
    .then(({ result }) => {
      setTitle(result.title)
    })

  return (
    <>
      <PageTitle pageTitle={title} />
      <ScheduleComponent height='80vh'>
        <Inject
          services={[Day, Week, Month, Agenda, Resize, DragAndDrop]}
        />
      </ScheduleComponent>
    </>
  )
}

export default Meeting
