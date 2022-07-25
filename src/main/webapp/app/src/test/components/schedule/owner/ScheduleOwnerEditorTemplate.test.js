import React from 'react'
import { render } from '@testing-library/react'
import { SAMPLE_EVENT } from '../../../mocks/events'
import { setupForSyncfusionTest } from '../../../test-core/util'
import ScheduleOwnerEditorTemplate
  from '../../../../components/schedule/owner/ScheduleOwnerEditorTemplate'
import { beforeAll, describe, expect, it } from '@jest/globals'

describe('ScheduleOwnerEditorTemplate', () => {
  beforeAll(setupForSyncfusionTest)

  it('should render correctly without data', () => {
    const { asFragment } = render(<ScheduleOwnerEditorTemplate
      id={1}
      timeSlotUserInfos={[]}
      {...SAMPLE_EVENT}
                                  />)
    expect(asFragment()).toMatchSnapshot()
  })

  it('should render correctly with user picks', () => {
    const { asFragment, getByText } = render(<ScheduleOwnerEditorTemplate
      id={1}
      timeSlotUserInfos={[{ available: true, user: { email: 'test@test.com' } }]}
      {...SAMPLE_EVENT}
                                             />)
    expect(getByText('test@test.com')).toBeInTheDocument()
    expect(asFragment()).toMatchSnapshot()
  })

  it('should render correctly with solver allocations', () => {
    const { asFragment, getByText } = render(<ScheduleOwnerEditorTemplate
      id={1}
      timeSlotUserInfos={[
        {
          picked: true,
          user: { email: 'test@test.com' }
        }]}
      {...SAMPLE_EVENT}
                                             />)
    expect(getByText('test@test.com')).toBeInTheDocument()
    expect(asFragment()).toMatchSnapshot()
  })
})
