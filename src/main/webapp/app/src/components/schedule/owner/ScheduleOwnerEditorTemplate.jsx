import { DateTimePickerComponent } from '@syncfusion/ej2-react-calendars'
import * as React from 'react'
import _ from 'lodash'
import { List, ListItem, ListItemAvatar, ListItemText } from '@mui/material'
import Avatar from '@mui/material/Avatar'
import Typography from '@mui/material/Typography'
import { UI } from '../../../core/const'

const ScheduleOwnerEditorTemplate = (props) => {
  if (_.isEmpty(props)) {
    return <div />
  }

  const FieldEditorRow = ({ label, name, ...props }) => (
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

  const FieldList = ({ label, items, ...props }) => {
    const { SIZE } = UI.SCHEDULE.OWNER.EDITOR.AVATAR
    return (
      <tr>
        <td
          className='e-textlabel' style={{
            paddingTop: SIZE / 2,
            paddingBottom: SIZE / 2
          }}
        >
          {label}
        </td>
        <td colSpan={4}>
          {items.length > 0 && (
            <List dense {...props}>
              {items.map(item => (
                <ListItem>
                  <ListItemAvatar>
                    <Avatar
                      sx={{
                        width: SIZE,
                        height: SIZE
                      }}
                      src='/avatar-does-not-exist'
                    />
                  </ListItemAvatar>
                  <ListItemText>
                    {item}
                  </ListItemText>
                </ListItem>
              ))}
            </List>
          )}
          {items.length === 0 && (
            <Typography variant='body'>
              No records to display
            </Typography>
          )}
        </td>
      </tr>
    )
  }

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
        <FieldList
          label='Picked'
          items={timeSlotUserInfos.map(info => info.user.email)}
        />
        <FieldList
          label='Allocated'
          items={timeSlotUserInfos.filter(({ picked }) => picked /* FIXME fieldName */)
            .map(info => info.user.email)}
        />
      </tbody>
    </table>
  )
}

export default ScheduleOwnerEditorTemplate
