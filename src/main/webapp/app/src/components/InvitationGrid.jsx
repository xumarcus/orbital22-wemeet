import { DataManager } from '@syncfusion/ej2-data'
import {
  ColumnDirective,
  ColumnsDirective,
  Edit,
  GridComponent,
  Inject,
  Toolbar,
} from '@syncfusion/ej2-react-grids'
import RestAdaptor from '../core/RestAdaptor'

import { API, TOOLBAR } from '../core/const'
import Typography from '@mui/material/Typography'

const InvitationGrid = ({ rosterPlan }) => {
  const restAdaptorParams = {
    url: rosterPlan._links.rosterPlanUserInfos.href,
    map: (resp) => RestAdaptor.extendCounts(resp._embedded.rosterPlanUserInfo),
    crudUrl: API.ROSTER_PLAN_USER_INFO,
    crudMap: (req) => ({ ...req, rosterPlan: rosterPlan._links.self.href }),
  }

  if (restAdaptorParams.url === null) {
    throw new Error('Meeting not found.')
  }

  const dataManager = new DataManager({
    adaptor: new RestAdaptor(restAdaptorParams),
  })

  const editSettings = {
    allowAdding: true,
    allowEditing: true,
  }

  const lockedTemplate = ({ locked }) => (
    <Typography variant="body">
      {locked ? '🔐' : '🔓'}
    </Typography>
  )

  return (
    <GridComponent dataSource={dataManager} editSettings={editSettings}
                   toolbar={TOOLBAR}>
      <ColumnsDirective>
        <ColumnDirective field="email" headerText="Email" textAlign="Center"
                         isPrimaryKey/>
        <ColumnDirective field="locked" headerText="Locked" width="120"
                         textAlign="Center" template={lockedTemplate} displayAsCheckbox
                         editType="booleanEdit"/>
      </ColumnsDirective>
      <Inject services={[Edit, Toolbar]}/>
    </GridComponent>
  )
}

export default InvitationGrid
