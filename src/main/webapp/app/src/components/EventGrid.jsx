import { DataManager } from '@syncfusion/ej2-data'
import { ColumnDirective, ColumnsDirective, GridComponent, Edit, Inject, Toolbar } from '@syncfusion/ej2-react-grids'
import RestAdaptor from '../core/RestAdaptor'

import { TOOLBAR } from '../core/const'
import { useContext } from 'react'
import AppContext from '../core/AppContext'
import { extendWithID } from '../core/util'
import camelCaseKeys from 'camelcase-keys'

const EventGrid = () => {
  const { context } = useContext(AppContext)
  const respItemToObject = respItem => ({ ...extendWithID(respItem), Title: respItem.title })

  const get = {
    url: context.user._links.ownedRosterPlans.href,
    respToList: (resp) => resp._embedded.rosterPlan.map(respItemToObject)
  }

  const set = {
    url: '/api/rosterPlan',
    respItemToObject,
    columnNamesToObjectKeys: camelCaseKeys
  }

  if (get.url === null) {
    throw new Error('Please sign in.')
  }

  const dataManager = new DataManager({
    adaptor: new RestAdaptor(get, set)
  })

  const editSettings = {
    allowAdding: true
  }

  return (
    <GridComponent dataSource={dataManager} editSettings={editSettings} toolbar={TOOLBAR}>
      <ColumnsDirective>
        <ColumnDirective field='ID' headerText='ID' width='120' textAlign='Center' isPrimaryKey isIdentity />
        <ColumnDirective field='Title' headerText='Title' textAlign='Center' />
      </ColumnsDirective>
      <Inject services={[Edit, Toolbar]} />
    </GridComponent>
  )
}

export default EventGrid
