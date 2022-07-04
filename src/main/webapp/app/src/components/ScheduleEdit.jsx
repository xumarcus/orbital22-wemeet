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
import { fromTemplate } from '../core/util'

const ScheduleEdit = ({ rosterPlan }) => {
  const template = rosterPlan?._links?.timeSlots?.href
  if (template === null) {
    throw new Error('Meeting not found.')
  }

  const params = new URLSearchParams({ projection: 'timeSlotProjection' })
  const url = `${fromTemplate(template).url}?${params.toString()}`

  const map = (req) => ({ ...req, rosterPlan: rosterPlan?._links?.self.href })

  const dataManager = new DataManager({
    adaptor: new RestAdaptor({
      GET: RestAdaptor.get(url, resp => resp._embedded.timeSlot),
      POST: RestAdaptor.post(API.TIME_SLOT, map),
      PUT: RestAdaptor.put(API.TIME_SLOT, map),
      DELETE: RestAdaptor.delete(API.TIME_SLOT)
    })
  })

  const eventSettings = {
    dataSource: dataManager,

    // TODO backend refactoring
    fields: {
      startTime: { name: 'startDateTime' },
      endTime: { name: 'endDateTime' },
      subject: { name: 'capacity' } // FIXME Stand in
    }
  }

  return (
    <ScheduleComponent
      editorTemplate={ScheduleEditEditorTemplate}
      eventSettings={eventSettings}
      height='80vh'
    >
      <Inject
        services={[Day, Week, Month, Agenda, Resize, DragAndDrop]}
      />
    </ScheduleComponent>
  )
}

export default ScheduleEdit
