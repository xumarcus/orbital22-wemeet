import React from 'react'
import ReactDOM from 'react-dom/client'
import reportWebVitals from './reportWebVitals'
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'
import { registerLicense } from '@syncfusion/ej2-base'

import App from './App'
import Dashboard from './pages/Dashboard'
import Demo from './components/Demo'
import Error from './pages/Error'
import Guide from './pages/Guide'
import Home from './pages/Home'
import MeetingCreate from './pages/MeetingCreate'
import Profile from './pages/Profile'
import Wizard from './pages/Wizard'

import { SYNCFUSION_LICENSE_KEY } from './core/const'

registerLicense(SYNCFUSION_LICENSE_KEY)

const root = ReactDOM.createRoot(document.getElementById('root'))
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<App />}>
          <Route path='' element={<Home />} />

          {/* TODO */}
          <Route path='about' element={<Navigate to='/error' />} />{' '}

          <Route path='dashboard' element={<Dashboard />} />
          <Route path='demo' element={<Demo />} />
          <Route path='error' element={<Error />} />

          {/* TODO */}
          <Route path='features' element={<Navigate to='/error' />} />{' '}

          {/* TODO WIP */}
          <Route path='guide' element={<Guide />} />

          <Route path='meeting'>
            <Route path='create' element={<MeetingCreate />} />
          </Route>
          <Route path='profile' element={<Profile />} />

          {/* TODO is this needed? */}
          <Route path='wizard' element={<Wizard />} />
        </Route>
        <Route path='*' element={<Error />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
)

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals()
