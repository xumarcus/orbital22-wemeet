import React from 'react'
import { render, unmountComponentAtNode } from 'react-dom'
import { act } from 'react-dom/test-utils'
import Dashboard from '../pages/Dashboard'

describe('Dashboard', () => {
  it('should render correctly when not signed in', () => {
    const container = document.createElement('div')
    document.body.appendChild(container)

    act(() => {
      render(<Dashboard />, container)
    })

    expect(container.innerHTML).toMatchSnapshot()

    unmountComponentAtNode(container)
    container.remove()
  })
})


