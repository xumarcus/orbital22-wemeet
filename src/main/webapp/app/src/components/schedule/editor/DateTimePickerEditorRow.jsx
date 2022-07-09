import { DateTimePickerComponent } from '@syncfusion/ej2-react-calendars'
import * as React from 'react'
import EditorRow from './EditorRow'

const DateTimePickerEditorRow = ({ label, name, ...props }) => (
  <EditorRow label={label}>
    <DateTimePickerComponent
      className='e-field'
      format='dd/MM/yy hh:mm a'
      id={name}
      data-name={name}  {/* just metadata */}
      {...props}
    />
  </EditorRow>
)

export default  DateTimePickerEditorRow