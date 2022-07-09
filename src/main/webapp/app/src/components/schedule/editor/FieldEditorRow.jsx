import * as React from 'react'
import EditorRow from './EditorRow'

const FieldEditorRow = ({ label, name, ...props }) => (
  <EditorRow label={label}>
    <input
      className='e-field e-input'
      id={name}
      name={name}
      style={{ width: '100%' }}
      {...props}
    />
  </EditorRow>
)

export default FieldEditorRow