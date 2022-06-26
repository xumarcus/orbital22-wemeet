import {
  Agenda,
  Day,
  Inject,
  Month,
  ScheduleComponent,
  Week,
} from '@syncfusion/ej2-react-schedule'
import * as React from 'react'
import { DataManager } from '@syncfusion/ej2-data'
import RestAdaptor from '../core/RestAdaptor'
import ScheduleRankEditorTemplate from './ScheduleRankEditorTemplate'
import _ from 'lodash'
import { API } from '../core/const'

const ScheduleRank = ({ rosterPlan }) => {
  // Overrides update behavior, no crudUrl
  const restAdaptorParams = {
    url: rosterPlan?._links?.timeSlots?.href
      .replace('{?projection}', '?projection=timeSlotProjection'),
    map: (resp) => resp._embedded.timeSlot,
    crudUrl: API.TIME_SLOT_USER_INFO,
    crudMap: (req) => {
      if (_.isEmpty(req)) return null
      const { id: timeSlotId, rank } = req
      return {
        timeSlot: API.TIME_SLOT_ID(timeSlotId),
        rank
      }
    }
  }

  if (restAdaptorParams.url === null) {
    throw new Error('Meeting not found.')
  }

  const dataManager = new DataManager({
    adaptor: new RestAdaptor(restAdaptorParams)
  })

  const eventSettings = {
    dataSource: dataManager,

    fields: {
      startTime: { name: 'startDateTime' },
      endTime: { name: 'endDateTime' },
      subject: { name: 'capacity' } // FIXME Stand in
    }
  }

  return (
    <ScheduleComponent
      editorTemplate={ScheduleRankEditorTemplate}
      eventSettings={eventSettings}
      height='80vh'
    >
      <Inject
        services={[Day, Week, Month, Agenda]}
      />
    </ScheduleComponent>
  )
}

export default ScheduleRank
