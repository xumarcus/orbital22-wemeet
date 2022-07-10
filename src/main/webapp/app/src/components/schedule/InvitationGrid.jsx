import {DataManager} from '@syncfusion/ej2-data';
import {
  ColumnDirective,
  ColumnsDirective,
  Edit,
  GridComponent,
  Inject,
  Toolbar
} from '@syncfusion/ej2-react-grids';
import RestAdaptor from '../../core/RestAdaptor';

import {API, ERROR_MESSAGES, TOOLBAR} from '../../core/const';
import Typography from '@mui/material/Typography';
import {fromTemplate} from '../../core/util';

const InvitationGrid = ({ rosterPlan, readonly }) => {
  const template = rosterPlan._links.rosterPlanUserInfos.href
  if (template === null) throw new Error(ERROR_MESSAGES.MEETING_NOT_FOUND)

  const params = new URLSearchParams({ projection: 'rosterPlanUserInfoProjection' })
  const url = `${fromTemplate(template).url}?${params.toString()}`

  const dataManager = new DataManager({
    adaptor: new RestAdaptor({
      GET: RestAdaptor.get(url, resp => resp._embedded.rosterPlanUserInfo),
      POST: RestAdaptor.post(API.ROSTER_PLAN_USER_INFO,
        req => ({ ...req, rosterPlan: rosterPlan._links.self.href })),
      DELETE: RestAdaptor.delete(API.ROSTER_PLAN_USER_INFO, ({ key }) => key)
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
