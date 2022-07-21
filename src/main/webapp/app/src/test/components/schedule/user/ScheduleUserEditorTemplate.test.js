import React from 'react'
import { render } from '@testing-library/react'

import {
  getSelfRank,
  ScheduleUserEditorTemplateInner
} from '../../../../components/schedule/user/ScheduleUserEditorTemplate'
import { SAMPLE_EVENT } from '../../../mocks/events'
import { setupForSyncfusionTest } from '../../../test-core/util'

describe('ScheduleUserEditorTemplate', () => {
  beforeAll(setupForSyncfusionTest)

  it('should render correctly without rank', () => {
    const { asFragment } = render(<ScheduleUserEditorTemplateInner
      id={1}
      rank={getSelfRank({ id: 1 }, [])}
      {...SAMPLE_EVENT} />)
    expect(asFragment()).toMatchSnapshot()
  })

  it('should render correctly with info from self', () => {
    const { asFragment, getByText } = render(<ScheduleUserEditorTemplateInner
      id={1}
      rank={getSelfRank({ id: 1 }, [{
        user: { id: 1 },
        rank: 1989,
      }])}
      available
      {...SAMPLE_EVENT} />)
    expect(getByText('1989')).toBeInTheDocument()
    expect(asFragment()).toMatchSnapshot()
  })

  it('should render correctly with info not from self', () => {
    const { asFragment, queryByText } = render(<ScheduleUserEditorTemplateInner
      id={1}
      rank={getSelfRank({ id: 1 }, [{
        user: { id: 2 },
        rank: 8964,
      }])}
      available
      {...SAMPLE_EVENT} />)
    expect(queryByText('8964')).toBeNull()
    expect(asFragment()).toMatchSnapshot()
  })
})
