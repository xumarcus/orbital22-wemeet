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

const MeetingCreate = () => {
  return (
    <>
      <PageTitle pageTitle='Create a meeting' />
      <ScheduleComponent height='80vh'>
        <Inject
          services={[Day, Week, Month, Agenda, Resize, DragAndDrop]}
        />
      </ScheduleComponent>
    </>
  )
}

export default MeetingCreate
