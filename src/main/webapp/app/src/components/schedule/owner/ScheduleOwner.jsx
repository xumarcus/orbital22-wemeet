import {
  Agenda,
  Day,
  DragAndDrop,
  Inject,
  Month,
  Resize,
  ScheduleComponent,
  Week
} from '@syncfusion/ej2-react-schedule'
import * as React from 'react'
import { DataManager } from '@syncfusion/ej2-data'
import RestAdaptor from '../../../core/RestAdaptor'
import { API } from '../../../core/const'
import ScheduleOwnerEditorTemplate from './ScheduleOwnerEditorTemplate'
import { fromTemplate } from '../../../core/util'
import ScheduleEventFooter from '../ScheduleEventFooter'

const ScheduleOwner = ({ rosterPlan, readOnly, eventDuration }) => {
  const template = rosterPlan?._links?.timeSlots?.href
  if (template === null) {
    throw new Error('Meeting not found.')
  }

  const params = new URLSearchParams({ projection: API.PROJECTIONS.TIME_SLOT })
  const url = `${fromTemplate(template).url}?${params.toString()}`

  const map = ({
    capacity,
    startDateTime,
    endDateTime,
    ..._rest
  }) => ({
    capacity,
    startDateTime,
    endDateTime,
    rosterPlan: rosterPlan?._links?.self.href
  })

  const dataManager = new DataManager({
    adaptor: new RestAdaptor({
      GET: RestAdaptor.get(url, resp => resp._embedded.timeSlot),
      POST: RestAdaptor.post(API.TIME_SLOT, map),
      PUT: RestAdaptor.put(API.TIME_SLOT, map),
      DELETE: RestAdaptor.delete(API.TIME_SLOT, ({ id }) => id)
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

  const onEventRendered = ({ data: { timeSlotUserInfos }, element }) => {
    // If Resize is injected, resize helper is prepended to element.children
    const appointment = element.children[readOnly ? 0 : 1]
    appointment.append(ScheduleEventFooter(timeSlotUserInfos))
  }

  const services = readOnly
    ? [Day, Week, Month, Agenda]
    : [Day, Week, Month, Agenda, Resize, DragAndDrop]

  const timeScale = {
    enabled: true,
    interval: 60,
    slotCount: 60 / eventDuration
  }

  return (
    <ScheduleComponent
      cssClass='schedule-cell-dimension'
      editorTemplate={ScheduleOwnerEditorTemplate}
      eventRendered={onEventRendered}
      eventSettings={eventSettings}
      height='80vh'
      timeScale={timeScale}
    >
      <Inject services={services} />
    </ScheduleComponent>
  )
}

export default ScheduleOwner
