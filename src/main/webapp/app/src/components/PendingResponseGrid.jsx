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

import { API, ROUTES, TOOLBAR } from '../core/const'
import { useContext } from 'react'
import AppContext from '../core/AppContext'
import { Link } from 'react-router-dom'

const PendingResponseGrid = () => {
  const { context } = useContext(AppContext)

  const url = context.user._links.rosterPlanUserInfos.href
    .replace('{?projection}', '?projection=rosterPlanUserInfoProjection')
  if (url === null) {
    throw new Error('Please sign in.')
  }

  const dataManager = new DataManager({
    adaptor: new RestAdaptor({
      GET: RestAdaptor.get(url, resp => resp._embedded.rosterPlanUserInfo),
      DELETE: RestAdaptor.delete(API.ROSTER_PLAN)
    })
  })

  const editSettings = {
    allowDeleting: true
  }

  const linkIDTemplate = ({ rosterPlan: { id } }) => (
    id && <Link to={ROUTES.MEETING_RANK(id)}>{id}</Link>
  )

  const emailTemplate = (x) => {
    console.log(x)
    // return (id === context.user.id ? TEXT.YOURSELF : <Link to={`mailto:${email}`}>{email}</Link>)
  }

  return (
    <GridComponent dataSource={dataManager} editSettings={editSettings} toolbar={TOOLBAR}>
      <ColumnsDirective>
        <ColumnDirective field='rosterPlan.id' headerText='ID' template={linkIDTemplate} width='120' textAlign='Center' isPrimaryKey isIdentity />
        <ColumnDirective field='rosterPlan.title' headerText='Title' textAlign='Center' />
        <ColumnDirective field='user.email' headerText='From' template={emailTemplate} textAlign='Center' />
      </ColumnsDirective>
      <Inject services={[Edit, Toolbar]} />
    </GridComponent>
  )
}

export default PendingResponseGrid
