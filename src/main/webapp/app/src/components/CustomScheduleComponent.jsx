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

const CustomScheduleComponent = ({ rosterPlan, readonly }) => {
  const restAdaptorParams = {
    url: rosterPlan?._links?.timeSlots?.href,
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
    <ScheduleComponent height="80vh" eventSettings={eventSettings}
                       readonly={readonly}>
      <Inject
        services={readonly
          ? [Day, Week, Month, Agenda]
          : [Day, Week, Month, Agenda, Resize, DragAndDrop]}
      />
    </ScheduleComponent>
  )
}

export default CustomScheduleComponent
