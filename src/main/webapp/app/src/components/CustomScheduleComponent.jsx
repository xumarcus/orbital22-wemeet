import {
  Agenda,
  Day,
  DragAndDrop,
  Inject,
  Month,
  Resize,
  ScheduleComponent,
  Week
} from '@syncfusion/ej2-react-schedule'
import * as React from 'react'
import { DataManager } from '@syncfusion/ej2-data'
import RestAdaptor from '../core/RestAdaptor'
import { API } from '../core/const'
import { DateTimePickerComponent } from '@syncfusion/ej2-react-calendars'
import { DropDownListComponent } from '@syncfusion/ej2-react-dropdowns'

const CustomScheduleComponent = ({ rosterPlan, readonly }) => {
  const restAdaptorParams = {
    url: rosterPlan?._links?.timeSlots?.href,
    map: (resp) => resp._embedded.timeSlot,
    crudUrl: API.TIME_SLOT,
    crudMap: (req) => ({ ...req, rosterPlan: rosterPlan?._links?.self.href })
  }

  if (restAdaptorParams.url === null) {
    throw new Error('Meeting not found.')
  }

  const dataManager = new DataManager({
    adaptor: new RestAdaptor(restAdaptorParams)
  })

  const eventSettings = {
    dataSource: dataManager,

    // TODO backend refactoring
    fields: {
      startTime: { name: 'startDateTime' },
      endTime: { name: 'endDateTime' },
      subject: { name: 'capacity' } // FIXME Stand in
    }
  }

  const onPopupOpen = args => {
    if (args.type === 'Editor') {
      const statusElement = args.element.querySelector('#EventType')
      statusElement.setAttribute('name', 'EventType')
    }
  }

  const editorTemplate = props => {
    return (
      <><table className='custom-event-editor' style={{ width: '100%', cellpadding: '5' }}><tbody>
        <tr>
          <td className='e-textlabel'>Title</td>
          <td colSpan={4}>
            <input id='Title' className='e-field e-input' type='text' name='title' style={{ width: '100%' }} />
          </td>
        </tr>
        <tr>
          <td className='e-textlabel'>Rank</td>
          <td colSpan={4}>
            <input id='Rank' className='e-field e-input' type='number' name='rank' style={{ width: '100%' }} />
          </td>
        </tr>
        <tr>
          <td className='e-textlabel'>Available Slots</td>
          <td colSpan={4}>
            <input id='Occupancy' className='e-field e-input' type='number' name='occupancy' style={{ width: '100%' }} />
          </td>
        </tr>
        <tr>
          <td className='e-textlabel'>Status</td>
          <td colSpan={4}>
            <DropDownListComponent id='Status' placeholder='Choose status' data-name='EventStatus' className='e-field' style={{ width: '100%' }} dataSource={['Open', 'Confirmed']} value={props.EventType || null} />
          </td>
        </tr>
        <tr>
          <td className='e-textlabel'>From</td>
          <td colSpan={4}>
            <DateTimePickerComponent format='dd/MM/yy hh:mm a' id='StartTime' data-name='StartTime' value={new Date(props.startTime || props.StartTime)} className='e-field' />
          </td>
        </tr>
        <tr>
          <td className='e-textlabel'>To</td>
          <td colSpan={4}>
            <DateTimePickerComponent format='dd/MM/yy hh:mm a' id='EndTime' data-name='EndTime' value={new Date(props.endTime || props.EndTime)} className='e-field' />
          </td>
        </tr>
        <tr>
          <td className='e-textlabel'>Attendee</td>
          <td colSpan={4}>
            <textarea id='Attendee' className='e-field e-input' name='Attendee' rows={3} cols={50} style={{ width: '100%', height: '60px !important', resize: 'vertical' }} />
          </td>
        </tr>
      </tbody>
      </table>
        <div />
      </>
    )
  }

  return (
    <ScheduleComponent
      editorTemplate={editorTemplate}
      eventSettings={eventSettings}
      height='80vh'
      popupOpen={onPopupOpen}
    >
      <Inject
        services={readonly
          ? [Day, Week, Month, Agenda]
          : [Day, Week, Month, Agenda, Resize, DragAndDrop]}
      />
    </ScheduleComponent>
  )
}

export default CustomScheduleComponent
