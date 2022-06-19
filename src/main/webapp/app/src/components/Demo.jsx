import * as React from 'react'
import {
  Agenda,
  Day,
  Inject,
  Month,
  ScheduleComponent,
  Week,
  WorkWeek,
} from '@syncfusion/ej2-react-schedule'

import '../../node_modules/@syncfusion/ej2-base/styles/material.css'
import '../../node_modules/@syncfusion/ej2-buttons/styles/material.css'
import '../../node_modules/@syncfusion/ej2-calendars/styles/material.css'
import '../../node_modules/@syncfusion/ej2-dropdowns/styles/material.css'
import '../../node_modules/@syncfusion/ej2-inputs/styles/material.css'
import '../../node_modules/@syncfusion/ej2-lists/styles/material.css'
import '../../node_modules/@syncfusion/ej2-navigations/styles/material.css'
import '../../node_modules/@syncfusion/ej2-popups/styles/material.css'
import '../../node_modules/@syncfusion/ej2-splitbuttons/styles/material.css'
import '../../node_modules/@syncfusion/ej2-react-schedule/styles/material.css'

const Demo = () => {
  return (
    <ScheduleComponent>
      <Inject services={[Day, Week, WorkWeek, Month, Agenda]}/>
    </ScheduleComponent>
  )
}

export default Demo
