import * as React from 'react'

// Copied from Syncfusion
const EditorRow = ({ label, children }) => (
  <tr>
    <td className='e-textlabel'>{label}</td>
    <td colSpan={4}>
      {children}
    </td>
  </tr>
)

export default EditorRow
