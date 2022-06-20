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
import { useContext } from 'react'
import AppContext from '../core/AppContext'
import { Link } from 'react-router-dom'

const EventGrid = () => {
  const { context } = useContext(AppContext)

  const restAdaptorParams = {
    url: context.user._links.ownedRosterPlans.href,
    map: (resp) => RestAdaptor.extendCounts(resp._embedded.rosterPlan),
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
    allowAdding: true
  }

  const linkIDTemplate = ({ id }) => (
    id && <Link to={`/meeting/${id}`}>{id}</Link>
  )

  return (
    <GridComponent dataSource={dataManager} editSettings={editSettings} toolbar={TOOLBAR}>
      <ColumnsDirective>
        <ColumnDirective field='id' headerText='ID' template={linkIDTemplate} width='120' textAlign='Center' isPrimaryKey isIdentity />
        <ColumnDirective field='title' headerText='Title' textAlign='Center' />
      </ColumnsDirective>
      <Inject services={[Edit, Toolbar]} />
    </GridComponent>
  )
}

export default EventGrid
