import { DateTimePickerComponent } from '@syncfusion/ej2-react-calendars'
import * as React from 'react'
import { useContext } from 'react'
import _ from 'lodash'
import AppContext from '../core/AppContext'

const ScheduleRankEditorTemplate = (props) => {
  const { context } = useContext(AppContext)

  if (_.isEmpty(props)) {
    return <div></div>
  }

  const RankEditorRow = ({ label, name, ...props }) => (
    <tr>
      <td className="e-textlabel">{label}</td>
      <td colSpan={4}>
        <input
          className="e-field e-input"
          id={name}
          name={name}
          style={{ width: '100%' }}
          {...props}
        />
      </td>
    </tr>
  )

  const {
    id: timeSlotId,
    // Id: syncfusionId,
    startDateTime,
    endDateTime,
    capacity,
    timeSlotUserInfos,
  } = props

  // TODO refactor into BE
  const rank = timeSlotUserInfos.find(
    info => info.user.id === context.user.id)?.rank

  return (
    <table className="custom-event-editor"
           style={{ width: '100%', cellpadding: '5' }}>


      <tbody>
      <RankEditorRow label="TimeSlotId" name="timeSlotId" type="number"
                     value={timeSlotId} disabled hidden/>
      <RankEditorRow label="Capacity" name="capacity" value={capacity}
                     disabled/>
      <RankEditorRow label="Rank" name="rank" defaultValue={rank}
                     type="number"/>

      <tr>
        <td className="e-textlabel">From</td>
        <td colSpan={4}>
          <DateTimePickerComponent
            className="e-field"
            format="dd/MM/yy hh:mm a"
            id="startDateTime"
            data-name="startDateTime"
            value={startDateTime}
            disabled
          />
        </td>
      </tr>
      <tr>
        <td className="e-textlabel">To</td>
        <td colSpan={4}>
          <DateTimePickerComponent
            className="e-field"
            format="dd/MM/yy hh:mm a"
            id="endDateTime"
            data-name="endDateTime"
            value={endDateTime}
            disabled
          />
        </td>
      </tr>
      </tbody>
    </table>
  )
}

export default ScheduleRankEditorTemplate