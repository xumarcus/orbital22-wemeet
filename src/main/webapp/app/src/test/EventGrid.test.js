import React from 'react'
import { render } from '@testing-library/react'
import { EventGridInner } from '../components/dashboard/EventGrid'
import { BrowserRouter } from 'react-router-dom'
import { setupForSyncfusionTest } from './core/util'

const sleep = ms => new Promise(resolve => setTimeout(resolve, ms))

describe('EventGrid', () => {
  // Syncfusion
  beforeAll(setupForSyncfusionTest)

  it('should render correctly without data', () => {
    const { asFragment } = render(<EventGridInner dataSource={[]} />)
    expect(asFragment()).toMatchSnapshot()
  })

  it('should render correctly with data', async () => {
    const { asFragment, getByText } = render(
      <BrowserRouter><EventGridInner
        dataSource={[{ id: 1, title: 'Hi' }]} /></BrowserRouter>)
    await sleep(500)
    expect(asFragment()).toMatchSnapshot()
    expect(getByText('Hi')).toBeInTheDocument()
  })
})
