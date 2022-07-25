import React from 'react'
import { render } from 'react-dom'
import reportWebVitals from './reportWebVitals'
import { registerLicense } from '@syncfusion/ej2-base'

import App from './App'

import { SYNCFUSION_LICENSE_KEY } from './core/const'

registerLicense(SYNCFUSION_LICENSE_KEY)

render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
)

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals()
