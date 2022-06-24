import * as React from 'react'
import { useEffect, useState } from 'react'
import CssBaseline from '@mui/material/CssBaseline'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Footer from './components/Footer'
import NavBar from './components/NavBar'
import Container from '@mui/material/Container'
import AppContext, { defaultAppContext } from './core/AppContext'
import { ErrorBoundary } from 'react-error-boundary'
import ErrorFallback from './components/ErrorFallback'

import './App.css'

import Home from './pages/Home'
import Dashboard from './pages/Dashboard'
import Demo from './components/Demo'
import Guide from './pages/Guide'
import MeetingEdit from './pages/MeetingEdit'
import Profile from './pages/Profile'
import Wizard from './pages/Wizard'
import { API } from './core/const'
import ajax from './core/ajax'
import MeetingRank from './pages/MeetingRank'

const App = () => {
  const [context, setContext] = useState(defaultAppContext)

  useEffect(() => {
    ajax('GET')(API.ME).then(user => setContext({ ...context, user }))
  }, [])

  return (
    <>
      <CssBaseline />

      <BrowserRouter>
        <AppContext.Provider value={{ context, setContext }}>
          <NavBar />
          <Container maxWidth='xl'>
            <ErrorBoundary FallbackComponent={ErrorFallback}>
              <Routes>
                <Route path='' element={<Home />} />

                {/* TODO <Route path='about' /> */}
                <Route path='dashboard' element={<Dashboard />} />
                <Route path='demo' element={<Demo />} />

                {/* TODO <Route path='features' /> */}

                {/* TODO WIP */}
                <Route path='guide' element={<Guide />} />

                <Route path='meeting'>
                  <Route path=':meetingId/edit' element={<MeetingEdit />} />
                  <Route path=':meetingId/rank' element={<MeetingRank />} />
                </Route>
                <Route path='profile' element={<Profile />} />

                {/* TODO is this needed? */}
                <Route path='wizard' element={<Wizard />} />
                <Route path='*' element={<ErrorFallback />} />
              </Routes>
            </ErrorBoundary>
          </Container>
        </AppContext.Provider>
      </BrowserRouter>

      <Footer />
    </>
  )
}

export default App
