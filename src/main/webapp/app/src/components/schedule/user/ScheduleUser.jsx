import {
  Agenda,
  Day,
  Inject,
  Month,
  ScheduleComponent,
  Week
} from '@syncfusion/ej2-react-schedule'
import * as React from 'react'
import { useContext } from 'react'
import { DataManager } from '@syncfusion/ej2-data'
import RestAdaptor from '../../../core/RestAdaptor'
import ScheduleUserEditorTemplate from './ScheduleUserEditorTemplate'
import { API } from '../../../core/const'
import AppContext from '../../../core/AppContext'
import { fromTemplate } from '../../../core/util'
import { getSelfInfoColor, makeScheduleEventFooter } from '../appearance'

const ScheduleUser = ({ rosterPlan }) => {
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
    element.style.backgroundColor = getSelfInfoColor(context.user, timeSlotUserInfos)

    const [appointment] = element.children
    appointment.append(makeScheduleEventFooter(timeSlotUserInfos))
  }

  return (
    <ScheduleComponent
      editorTemplate={ScheduleUserEditorTemplate}
      eventRendered={onEventRendered}
      eventSettings={eventSettings}
      height='80vh'
      locale='en-US'
      showQuickInfo={false}
    >
      <Inject
        services={[Day, Week, Month, Agenda]}
      />
    </ScheduleComponent>
  )
}

export default ScheduleUser
