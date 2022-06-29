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

  const restAdaptorParams = {
    url: context.user._links.rosterPlanUserInfos.href,
    map: (resp) => RestAdaptor.extendCounts(resp._embedded.rosterPlanUserInfo),
    crudUrl: API.ROSTER_PLAN,
    crudMap: (req) => req
  }

  if (restAdaptorParams.url === null) {
    throw new Error('Please sign in.')
  }

  const dataManager = new DataManager({
    adaptor: new RestAdaptor(restAdaptorParams)
  })

  const editSettings = {
    // TODO add delete?
  }

  const linkIDTemplate = ({ rosterPlan: { id } }) => (
    id && <Link to={ROUTES.MEETING_RANK(id)}>{id}</Link>
  )

  const linkEmailTemplate = ({ user: { id, email } }) => (
    id === context.user.id ? 'Yourself' : email
  )

  return (
    <GridComponent dataSource={dataManager} editSettings={editSettings} toolbar={TOOLBAR}>
      <ColumnsDirective>
        <ColumnDirective field='rosterPlan.id' headerText='ID' template={linkIDTemplate} width='120' textAlign='Center' isPrimaryKey isIdentity />
        <ColumnDirective field='rosterPlan.title' headerText='Title' textAlign='Center' />
        <ColumnDirective field='user.email' headerText='From' template={linkEmailTemplate} textAlign='Center' />
      </ColumnsDirective>
      <Inject services={[Edit, Toolbar]} />
    </GridComponent>
  )
}

export default PendingResponseGrid
