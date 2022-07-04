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

import { API, ROUTES } from '../core/const'
import { Link } from 'react-router-dom'
import Button from '@mui/material/Button'
import * as React from 'react'
import { useRef } from 'react'
import ajax from '../core/ajax'

const SolutionGrid = ({ rosterPlan }) => {
  const ref = useRef(null)

  const url = API.ROSTER_PLAN_BY_PARENT(rosterPlan._links.self.href)

  const dataManager = new DataManager({
    adaptor: new RestAdaptor({
      GET: RestAdaptor.get(url, resp => resp._embedded.rosterPlan)
    })
  })

  const handleGenerate = async () => {
    await ajax('POST', { title: 'TODO', parent: rosterPlan._links.self.href })(API.ROSTER_PLAN)
    ref.current.refresh()
  }

  const linkIDTemplate = ({ id }) => (
    id && <Link to={ROUTES.MEETING_VIEW_SOLUTION(id)}>{id}</Link>
  )

  return (
    <>
      <Button
        sx={{ my: 2 }} variant='contained' color='success'
        onClick={handleGenerate}
      >Generate
        Roster
      </Button>
      <GridComponent dataSource={dataManager} ref={g => ref.current = g}>
        <ColumnsDirective>
          <ColumnDirective
            field='id' headerText='ID' template={linkIDTemplate}
            width='120' textAlign='Center' isPrimaryKey
            isIdentity
          />
          <ColumnDirective field='title' headerText='Title' textAlign='Center' />
        </ColumnsDirective>
        <Inject services={[Edit, Toolbar]} />
      </GridComponent>
    </>
  )
}

export default SolutionGrid
