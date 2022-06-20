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

import { TOOLBAR } from '../core/const'

const InvitationGrid = ({ rosterPlan }) => {
  const restAdaptorParams = {
    url: rosterPlan._links.rosterPlanUserInfos,
    map: (resp) => RestAdaptor.extendCounts(resp._embedded.rosterPlanUserInfo),
    crudUrl: '/api/rosterPlanUserInfo',
    crudMap: (req) => req
  }

  if (restAdaptorParams.url === null) {
    throw new Error('Meeting not found.')
  }

  const dataManager = new DataManager({
    adaptor: new RestAdaptor(restAdaptorParams)
  })

  const editSettings = {
    allowAdding: true
  }

  const lockedTemplate = ({ locked }) => locked ? '🔐' : '🔓'

  return (
    <GridComponent dataSource={dataManager} editSettings={editSettings} toolbar={TOOLBAR}>
      <ColumnsDirective>
        <ColumnDirective field='email' headerText='Email' textAlign='Center' isPrimaryKey isIdentity />
        <ColumnDirective field='locked' headerText='Locked' template={lockedTemplate} width='120' textAlign='Center' />
      </ColumnsDirective>
      <Inject services={[Edit, Toolbar]} />
    </GridComponent>
  )
}

export default InvitationGrid
