import React from 'react'
import { render } from '@testing-library/react'
import { setupForSyncfusionTest } from './core/util'
import { ScheduleOwnerInner } from '../components/schedule/owner/ScheduleOwner'

const sleep = ms => new Promise(resolve => setTimeout(resolve, ms))

const CAPACITY = 1337
const TUESDAY = new Date(2019, 6, 6, 12, 0, 0)
const FRIDAY_START = new Date(2019, 6, 9, 14, 20, 0)
const FRIDAY_END = new Date(2019, 6, 9, 22, 0, 0)

describe('ScheduleOwner', () => {
  beforeAll(setupForSyncfusionTest)

  it('should render correctly without data', () => {
    const { asFragment } = render(<ScheduleOwnerInner dataSource={[]}
                                                      eventDuration={30} />)
    expect(asFragment()).toMatchSnapshot()
  })

  it('should render correctly with empty user info', async () => {
    const { asFragment } = render(
      <ScheduleOwnerInner
        dataSource={[
          {
            id: 1,
            capacity: CAPACITY,
            startDateTime: FRIDAY_START,
            endDateTime: FRIDAY_END
          }
        ]}
        eventDuration={15}
        selectedDate={TUESDAY}
      />)

    await sleep(500)
    expect(asFragment()).toMatchSnapshot()
  })
})
