import * as React from 'react'
import { useContext, useState } from 'react'
import _ from 'lodash'
import AppContext from '../../../core/AppContext'
import FieldEditorRow from '../editor/FieldEditorRow'
import SwitchEditorRow from '../editor/SwitchEditorRow'
import DateTimePickerEditorRow from '../editor/DateTimePickerEditorRow'

const ScheduleUserEditorTemplate = (props) => {
  const { context } = useContext(AppContext)

  if (_.isEmpty(props)) {
    return <div /> // Throw?
  }

  // const syncfusionId = props.Id
  props.rank = props
    ?.timeSlotUserInfos
    ?.find(info => info.user.id === context.user.id)
    ?.rank

  return <Inner {...props} />
}

const Inner = ({
  id: timeSlotId,
  startDateTime,
  endDateTime,
  capacity,
  available,
  rank
}) => {
  const [isRankDisabled, setIsRankDisabled] = useState(!available)

  const onAvailableChange = ({ checked }) => {
    setIsRankDisabled(!checked)
  }

  return (
    <table
      className='custom-event-editor'
      style={{ width: '100%', cellpadding: '5' }}
    >
      <tbody>
        <FieldEditorRow
          name='timeSlotId' type='number' value={timeSlotId}
          disabled hidden
        />
        <FieldEditorRow
          label='Capacity' name='capacity' value={capacity}
          disabled
        />
        <SwitchEditorRow
          label='Available' name='available' checked={available}
          change={onAvailableChange}
        />
        <FieldEditorRow
          label='Rank' name='rank' defaultValue={rank}
          type='number' disabled={isRankDisabled}
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
