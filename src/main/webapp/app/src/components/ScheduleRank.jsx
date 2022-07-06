import {
  Agenda,
  Day,
  Inject,
  Month,
  ScheduleComponent,
  Week,
} from '@syncfusion/ej2-react-schedule'
import * as React from 'react'
import { useContext } from 'react'
import { DataManager } from '@syncfusion/ej2-data'
import RestAdaptor from '../core/RestAdaptor'
import ScheduleRankEditorTemplate from './ScheduleRankEditorTemplate'
import { API, SYSTEM_THEME } from '../core/const'
import AppContext from '../core/AppContext'
import { fromTemplate } from '../core/util'

const ScheduleRank = ({ rosterPlan }) => {
  const { context } = useContext(AppContext)

  const template = rosterPlan?._links?.timeSlots?.href
  if (template === null) {
    throw new Error('Meeting not found.')
  }

  const params = new URLSearchParams({ projection: API.PROJECTIONS.TIME_SLOT })
  const url = `${fromTemplate(template).url}?${params.toString()}`

  const map = ({ id: timeSlotId, rank }) => {
    // if (_.isEmpty(req)) return null
    return {
      timeSlot: API.TIME_SLOT_ID(timeSlotId),
      rank
    }
  }

  const dataManager = new DataManager({
    adaptor: new RestAdaptor({
      GET: RestAdaptor.get(url, resp => resp._embedded.timeSlot),
      POST: RestAdaptor.post(API.TIME_SLOT_USER_INFO, map),
      PUT: RestAdaptor.put(API.TIME_SLOT_USER_INFO, map)
    })
  })

  const eventSettings = {
    dataSource: dataManager,

    fields: {
      startTime: { name: 'startDateTime' },
      endTime: { name: 'endDateTime' },
      subject: { name: 'capacity' } // FIXME Stand in
    }
  }

  const onEventRendered = ({ data: { timeSlotUserInfos }, element }) => {
    const info = timeSlotUserInfos.find(({ picked, user: { id } }) => picked && id === context.user.id)
    if (info) {
      element.style.backgroundColor = SYSTEM_THEME.palette.success.main
    }
  }

  return (
    <ScheduleComponent
      editorTemplate={ScheduleRankEditorTemplate}
      eventSettings={eventSettings}
      height='80vh'
      eventRendered={onEventRendered}
    >
      <Inject
        services={[Day, Week, Month, Agenda]}
      />
    </ScheduleComponent>
  )
}

export default ScheduleRank
