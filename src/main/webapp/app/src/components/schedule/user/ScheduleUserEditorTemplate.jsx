import { DateTimePickerComponent } from '@syncfusion/ej2-react-calendars'
import * as React from 'react'
import { useContext } from 'react'
import _ from 'lodash'
import AppContext from '../../../core/AppContext'

const ScheduleUserEditorTemplate = (props) => {
  const { context } = useContext(AppContext)

  if (_.isEmpty(props)) {
    return <div />
  }

  const RankEditorRow = ({ label, name, ...props }) => (
    <tr>
      <td className='e-textlabel'>{label}</td>
      <td colSpan={4}>
        <input
          className='e-field e-input'
          id={name}
          name={name}
          style={{ width: '100%' }}
          {...props}
        />
      </td>
    </tr>
  )

  const DateTimePickerEditorRow = ({ label, name, ...props }) => (
    <tr>
      <td className='e-textlabel'>{label}</td>
      <td colSpan={4}>
        <DateTimePickerComponent
          className='e-field'
          format='dd/MM/yy hh:mm a'
          id={name}
          data-name={name}
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
    timeSlotUserInfos
  } = props

  const rank = timeSlotUserInfos.find(
    info => info.user.id === context.user.id)?.rank

  return (
    <table
      className='custom-event-editor'
      style={{ width: '100%', cellpadding: '5' }}
    >
      <tbody>
        <RankEditorRow
          name='timeSlotId' type='number'
          value={timeSlotId} disabled hidden
        />
        <RankEditorRow
          label='Capacity' name='capacity' value={capacity}
          disabled
        />
        <RankEditorRow
          label='Rank' name='rank' defaultValue={rank}
          type='number'
        />
        <DateTimePickerEditorRow
          label='From' name='startDateTime' value={startDateTime}
          disabled
        />
        <DateTimePickerEditorRow
          label='To' name='endDateTime' value={endDateTime}
          disabled
        />
      </tbody>
    </table>
  )
}

export default ScheduleUserEditorTemplate
