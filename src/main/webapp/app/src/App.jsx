import * as React from 'react'
import { useEffect, useState } from 'react'
import CssBaseline from '@mui/material/CssBaseline'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Footer from './components/footer/Footer'
import NavBar from './components/navbar/NavBar'
import Container from '@mui/material/Container'
import AppContext, { defaultAppContext } from './core/AppContext'
import { ErrorBoundary } from 'react-error-boundary'
import ErrorFallback from './components/core/ErrorFallback'

import './App.css'

import Home from './pages/Home'
import Dashboard from './pages/Dashboard'
import MeetingEdit from './pages/MeetingEdit'
import { API } from './core/const'
import ajax from './core/ajax'
import MeetingRank from './pages/MeetingRank'
import MeetingViewSolution from './pages/MeetingViewSolution'

const App = () => {
  const [context, setContext] = useState(defaultAppContext)

  useEffect(() => {
    ajax('GET')(API.ME).then(user => setContext(c => ({ ...c, user })))
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
                <Route path='dashboard' element={<Dashboard />} />
                <Route path='meeting'>
                  <Route path=':meetingId/edit' element={<MeetingEdit />} />
                  <Route path=':meetingId/rank' element={<MeetingRank />} />
                  <Route
                    path=':meetingId/view-solution'
                    element={<MeetingViewSolution />}
                  />
                </Route>
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
