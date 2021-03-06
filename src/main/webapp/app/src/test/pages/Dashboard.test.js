import React from 'react'
import { render } from '@testing-library/react'
import { DashboardInner } from '../../pages/Dashboard'
import ErrorComponent from '../test-core/ErrorComponent'
import { describe, expect, it } from '@jest/globals'

describe('Dashboard', () => {
  it('should render correctly when no throw', () => {
    const { asFragment } = render(<DashboardInner
      eventGridComponent={null}
      pendingResponseGridComponent={null}
                                  />)
    expect(asFragment()).toMatchSnapshot()
  })

  it('should render correctly when throws', () => {
    const { asFragment } = render(<DashboardInner
      eventGridComponent={<ErrorComponent />}
      pendingResponseGridComponent={null}
                                  />)
    expect(asFragment()).toMatchSnapshot()
  })
})
