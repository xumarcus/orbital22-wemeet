import React from 'react'
import { render, unmountComponentAtNode } from 'react-dom'
import { act } from 'react-dom/test-utils'
import App from '../App'

/*
  There is not much point of unit testing when there is not much
  custom UI logic or client side validation. End-to-end tests are
  far more useful, but (...) asks us to have these tests anyway...
 */

// https://reactjs.org/docs/testing-recipes.html#snapshot-testing
describe('Main page', () => {
  it('should render correctly', () => {
    const container = document.createElement('div')
    document.body.appendChild(container)

    act(() => {
      render(<App />, container)
    })

    expect(container.innerHTML).toMatchSnapshot()

    unmountComponentAtNode(container)
    container.remove()
  })

  /*
  test('does not make scheduling hard', () => {
    render(<App />)
    expect(screen.getByText("Easy")).toBeInTheDocument()
    expect(screen.queryByText("Hard")).toBeNull()
  })
   */
})


