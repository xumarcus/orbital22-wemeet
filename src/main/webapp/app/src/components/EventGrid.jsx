import { DataManager } from '@syncfusion/ej2-data'
import { ColumnDirective, ColumnsDirective, GridComponent, Edit, Inject, Toolbar } from '@syncfusion/ej2-react-grids'
import RestAdaptor from '../core/RestAdaptor'

import { TOOLBAR } from '../core/const'
import { useContext } from 'react'
import AppContext from '../core/AppContext'
import Link from '@mui/material/Link'

const EventGrid = () => {
  const { context } = useContext(AppContext)

  const restAdaptorParams = {
    getUrl: context.user._links.ownedRosterPlans.href,
    map: (resp) => resp._embedded.rosterPlan,
    setUrl: '/api/rosterPlan'
  }

  if (restAdaptorParams.getUrl === null) {
    throw new Error('Please sign in.')
  }

  const dataManager = new DataManager({
    adaptor: new RestAdaptor(restAdaptorParams)
  })

  const editSettings = {
    allowAdding: true
  }

  const idTemplate = ({ id }) => (
    <Link href={`/meeting/${id}`}>{id}</Link>
  )

  return (
    <GridComponent dataSource={dataManager} editSettings={editSettings} toolbar={TOOLBAR}>
      <ColumnsDirective>
        <ColumnDirective field='id' headerText='ID' template={idTemplate} width='120' textAlign='Center' isPrimaryKey isIdentity />
        <ColumnDirective field='title' headerText='Title' textAlign='Center' />
      </ColumnsDirective>
      <Inject services={[Edit, Toolbar]} />
    </GridComponent>
  )
}

export default EventGrid
