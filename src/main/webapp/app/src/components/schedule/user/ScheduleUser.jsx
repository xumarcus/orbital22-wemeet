import {
  Agenda,
  Day,
  Inject,
  Month,
  ScheduleComponent,
  Week
} from '@syncfusion/ej2-react-schedule';
import * as React from 'react';
import {useContext} from 'react';
import {DataManager} from '@syncfusion/ej2-data';
import RestAdaptor from '../../../core/RestAdaptor';
import ScheduleUserEditorTemplate from './ScheduleUserEditorTemplate';
import {API, SYSTEM_THEME} from '../../../core/const';
import AppContext from '../../../core/AppContext';
import {fromTemplate} from '../../../core/util';
import ScheduleEventFooter from '../ScheduleEventFooter';

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

  const getSelfInfoColor = (timeSlotUserInfos) => {
    const info = timeSlotUserInfos.find(({ user: { id } }) => id === context.user.id)

    if (!info) return SYSTEM_THEME.palette.primary.main
    if (info.picked) return SYSTEM_THEME.palette.success.main
    switch (info.rank) {
      case 0:
        return SYSTEM_THEME.palette.primary.main // Same as info === null
      case 1:
        return SYSTEM_THEME.palette.warning.light
      case 2:
        return SYSTEM_THEME.palette.warning.main
      case 3:
        return SYSTEM_THEME.palette.warning.dark
      default:
        return SYSTEM_THEME.palette.error.main
    }
  }

  const onEventRendered = ({ data: { timeSlotUserInfos }, element }) => {
    element.style.backgroundColor = getSelfInfoColor(timeSlotUserInfos)

    const [appointment] = element.children
    appointment.append(ScheduleEventFooter(timeSlotUserInfos))
  }

  return (
    <ScheduleComponent
      editorTemplate={ScheduleUserEditorTemplate}
      eventSettings={eventSettings}
      height='80vh'
      eventRendered={onEventRendered}
      showQuickInfo={false}
    >
      <Inject
        services={[Day, Week, Month, Agenda]}
      />
    </ScheduleComponent>
  )
}

export default ScheduleUser
