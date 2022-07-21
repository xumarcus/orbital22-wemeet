import React from 'react'
import { render } from '@testing-library/react'
import { setupForSyncfusionTest } from '../../../test-core/util'
import {
  ScheduleOwnerInner
} from '../../../../components/schedule/owner/ScheduleOwner'

describe('ScheduleOwner', () => {
  beforeAll(setupForSyncfusionTest)

  it('should render correctly without data', () => {
    const { asFragment } = render(<ScheduleOwnerInner
      dataSource={[]}
      eventDuration={30}
                                  />)
    expect(asFragment()).toMatchSnapshot()
  })

  /*
  FIXME: failed to test with data

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

   */
})
