import { SwitchComponent } from '@syncfusion/ej2-react-buttons'
import * as React from 'react'
import EditorRow from './EditorRow'

const SwitchEditorRow = ({ label, name, ...props }) => (
  <EditorRow label={label}>
    <SwitchComponent
      className='e-field'
      id={name}
      data-name={name}
      {...props}
    />
  </EditorRow>
)

export default SwitchEditorRow
