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
import { fromTemplate } from '../core/util'

const EventGrid = () => {
  const { context } = useContext(AppContext)

  const url = fromTemplate(context.user._links.ownedRosterPlans.href).url
  if (url === null) {
    throw new Error('Please sign in.')
  }

  const dataManager = new DataManager({
    adaptor: new RestAdaptor({
      GET: RestAdaptor.get(url, resp => resp._embedded.rosterPlan),
      POST: RestAdaptor.post(API.ROSTER_PLAN),
      PUT: RestAdaptor.put(API.ROSTER_PLAN),
      DELETE: RestAdaptor.delete(API.ROSTER_PLAN)
    })
  })

  const editSettings = {
    allowAdding: true,
    allowEditing: true,
    allowDeleting: true
  }

  const linkIDTemplate = ({ id }) => (
    id && <Link to={ROUTES.MEETING_EDIT(id)}>{id}</Link>
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
