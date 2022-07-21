import React from 'react'
import { render } from '@testing-library/react'
import NavBar from '../../../components/navbar/NavBar'
import { BrowserRouter } from 'react-router-dom'

/*
  There is not much point of unit testing when there is not much
  custom UI logic or client side validation. End-to-end tests are
  far more useful, but (...) asks us to have these tests anyway...
 */

describe('NavBar', () => {
  it('should render correctly', () => {
    const { asFragment } = render(<BrowserRouter><NavBar /></BrowserRouter>)
    expect(asFragment()).toMatchSnapshot()
  })
})
