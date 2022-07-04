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

const InvitationGrid = ({ rosterPlan, readonly }) => {
  const url = rosterPlan._links.rosterPlanUserInfos.href
  if (url === null) {
    throw new Error('Meeting not found.')
  }

  const dataManager = new DataManager({
    adaptor: new RestAdaptor({
      GET: RestAdaptor.get(url, resp => resp._embedded.rosterPlanUserInfo),
      POST: RestAdaptor.post(API.ROSTER_PLAN_USER_INFO,
        req => ({ ...req, rosterPlan: rosterPlan._links.self.href })),
      DELETE: RestAdaptor.delete(API.ROSTER_PLAN_USER_INFO)
    })
  })

  const editSettings = {
    allowAdding: true,
    allowDeleting: true
  }

  const lockedTemplate = ({ locked }) => (
    <Typography variant='body'>
      {locked ? '🔐' : '🔓'}
    </Typography>
  )

  return (
    <GridComponent
      dataSource={dataManager} editSettings={editSettings}
      toolbar={readonly ? null : TOOLBAR}
    >
      <ColumnsDirective>
        <ColumnDirective field='id' visible={false} isPrimaryKey isIdentity />
        <ColumnDirective field='email' headerText='Email' textAlign='Center' />
        <ColumnDirective field='locked' headerText='Locked' width='120' textAlign='Center' template={lockedTemplate} displayAsCheckbox editType='booleanEdit' />
      </ColumnsDirective>
      {!readonly && <Inject services={[Edit, Toolbar]} />}
    </GridComponent>
  )
}

export default InvitationGrid
