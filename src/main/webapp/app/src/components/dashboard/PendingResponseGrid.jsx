import { DataManager } from '@syncfusion/ej2-data'
import {
  ColumnDirective,
  ColumnsDirective,
  Edit,
  GridComponent,
  Inject,
  Toolbar
} from '@syncfusion/ej2-react-grids'
import RestAdaptor from '../../core/RestAdaptor'

import { API, ROUTES, TEXT, TOOLBAR } from '../../core/const'
import { useContext } from 'react'
import AppContext from '../../core/AppContext'
import { Link } from 'react-router-dom'
import { fromTemplate } from '../../core/util'
import MaterialLink from '@mui/material/Link'

const PendingResponseGrid = () => {
  const { context } = useContext(AppContext)

  const template = context.user._links.rosterPlanUserInfos.href
  if (template === null) {
    throw new Error('Please sign in.')
  }

  const params = new URLSearchParams(
    { projection: API.PROJECTIONS.ROSTER_PLAN_USER_INFO })
  const url = `${fromTemplate(template).url}?${params.toString()}`

  const dataManager = new DataManager({
    adaptor: new RestAdaptor({
      GET: RestAdaptor.get(url, resp => resp._embedded.rosterPlanUserInfo),
      DELETE: RestAdaptor.delete(API.ROSTER_PLAN, ({ key }) => key)
    })
  })

  return <PendingResponseGridInner dataSource={dataManager} selfUserId={context.user.id} />
}

export const PendingResponseGridInner = ({ dataSource, selfUserId }) => {
  const editSettings = {
    allowDeleting: true
  }

  const linkIDTemplate = ({ rosterPlan: { id } }) => (
    id && <Link to={ROUTES.MEETING_RANK(id)}>{id}</Link>
  )

  const emailTemplate = ({ owner: { id, email } }) => id === selfUserId ? TEXT.YOURSELF : <MaterialLink to={`mailto:${email}`}>{email}</MaterialLink>

  return (
    <GridComponent dataSource={dataSource} editSettings={editSettings} toolbar={TOOLBAR}>
      <ColumnsDirective>
        <ColumnDirective field='rosterPlan.id' headerText='ID' template={linkIDTemplate} width='120' textAlign='Center' isPrimaryKey isIdentity />
        <ColumnDirective field='rosterPlan.title' headerText='Title' textAlign='Center' />
        <ColumnDirective field='email' headerText='From' template={emailTemplate} textAlign='Center' />
      </ColumnsDirective>
      <Inject services={[Edit, Toolbar]} />
    </GridComponent>
  )
}

export default PendingResponseGrid
