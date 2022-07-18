import React from 'react'
import { render } from '@testing-library/react'
import App from '../App'

/*
  There is not much point of unit testing when there is not much
  custom UI logic or client side validation. End-to-end tests are
  far more useful, but (...) asks us to have these tests anyway...
 */

describe('Main page', () => {
  it('should render correctly', () => {
    const { asFragment, getByText } = render(<App />)
    expect(asFragment()).toMatchSnapshot()
    expect(getByText('Easy')).toBeInTheDocument()
  })
})
