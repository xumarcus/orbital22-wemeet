import {
  Agenda,
  Day,
  DragAndDrop,
  Inject,
  Month,
  Resize,
  ScheduleComponent,
  Week,
} from '@syncfusion/ej2-react-schedule'
import * as React from 'react'
import { DataManager } from '@syncfusion/ej2-data'
import RestAdaptor from '../core/RestAdaptor'
import { API } from '../core/const'
import ScheduleEditEditorTemplate from './ScheduleEditEditorTemplate'

const ScheduleEdit = ({ rosterPlan }) => {
  const restAdaptorParams = {
    url: rosterPlan?._links?.timeSlots?.href
      .replace('{?projection}', '?projection=timeSlotProjection'),
    map: (resp) => resp._embedded.timeSlot,
    crudUrl: API.TIME_SLOT,
    crudMap: (req) => ({ ...req, rosterPlan: rosterPlan?._links?.self.href }),
  }

  if (restAdaptorParams.url === null) {
    throw new Error('Meeting not found.')
  }

  const dataManager = new DataManager({
    adaptor: new RestAdaptor(restAdaptorParams),
  })

  const eventSettings = {
    dataSource: dataManager,

    // TODO backend refactoring
    fields: {
      startTime: { name: 'startDateTime' },
      endTime: { name: 'endDateTime' },
      subject: { name: 'capacity' }, // FIXME Stand in
    },
  }

  return (
    <ScheduleComponent
      editorTemplate={ScheduleEditEditorTemplate}
      eventSettings={eventSettings}
      height="80vh"
    >
      <Inject
        services={[Day, Week, Month, Agenda, Resize, DragAndDrop]}
      />
    </ScheduleComponent>
  )
}

export default ScheduleEdit
