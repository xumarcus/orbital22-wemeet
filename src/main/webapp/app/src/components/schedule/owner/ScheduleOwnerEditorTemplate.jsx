import * as React from 'react'
import _ from 'lodash'
import FieldEditorRow from '../editor/FieldEditorRow'
import DateTimePickerEditorRow from '../editor/DateTimePickerEditorRow'
import EditorListWithAvatar from '../editor/EditorListWithAvatar'

const ScheduleOwnerEditorTemplate = (props) => {
  if (_.isEmpty(props)) {
    return <div />  // Throw?
  }

  const {
    id: timeSlotId,
    // Id: syncfusionId,
    startDateTime,
    endDateTime,
    capacity,
    timeSlotUserInfos
  } = props

  return (
    <table
      className='custom-event-editor'
      style={{ width: '100%', cellpadding: '5' }}
    >
      <tbody>
        <FieldEditorRow
          name='timeSlotId'
          type='number' value={timeSlotId} disabled hidden
        />
        <FieldEditorRow
          label='Capacity' name='capacity'
          defaultValue={capacity} type='number'
        />
        <DateTimePickerEditorRow
          label='From' name='startDateTime' value={startDateTime}
        />
        <DateTimePickerEditorRow
          label='To' name='endDateTime' value={endDateTime}
        />
        <EditorListWithAvatar
          label='Picked'
          items={timeSlotUserInfos.filter(({ available }) => available)
            .map(info => info.user.email)}
        />
        <EditorListWithAvatar
          label='Allocated'
          items={timeSlotUserInfos.filter(({ picked }) => picked /* FIXME fieldName */)
            .map(info => info.user.email)}
        />
      </tbody>
    </table>
  )
}

export default ScheduleOwnerEditorTemplate
