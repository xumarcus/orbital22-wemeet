import React from 'react'
import { render } from '@testing-library/react'
import { BrowserRouter } from 'react-router-dom'
import {
  PendingResponseGridInner
} from '../../../components/dashboard/PendingResponseGrid'
import { setupForSyncfusionTest } from '../../test-core/util'

const sleep = ms => new Promise(resolve => setTimeout(resolve, ms))

describe('PendingResponseGrid', () => {
  // Syncfusion
  beforeAll(setupForSyncfusionTest)

  it('should render correctly without data', () => {
    const { asFragment } = render(<PendingResponseGridInner
      dataSource={[]}
      selfUserId={null}
                                  />)
    expect(asFragment()).toMatchSnapshot()
  })

  it('should render correctly with data', async () => {
    const { asFragment, getByText } = render(
      <BrowserRouter>
        <PendingResponseGridInner
          dataSource={[
            {
              rosterPlan: { id: 1, title: 'Hello' },
              owner: { id: 3, email: 'test@test.com' }
            },
            {
              rosterPlan: { id: 2, title: 'World' },
              owner: { id: 4, email: 'not-test@test.com' }
            }
          ]}
          selfUserId={3}
        />
      </BrowserRouter>
    )
    await sleep(500)

    expect(getByText('Hello')).toBeInTheDocument()
    expect(getByText('Yourself')).toBeInTheDocument()

    expect(getByText('World')).toBeInTheDocument()
    expect(getByText('not-test@test.com')).toBeInTheDocument()

    expect(asFragment()).toMatchSnapshot()
  })
})
