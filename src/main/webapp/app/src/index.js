import React from 'react';
import ReactDOM from 'react-dom/client';
import reportWebVitals from './reportWebVitals';

import {BrowserRouter, Route, Routes} from 'react-router-dom';

import About from './pages/About';
import Dashboard from './pages/Dashboard';
import Features from './pages/Features';
import Guide from './pages/Guide';
import Home from './pages/Home';
import Profile from './pages/Profile';
import Wizard from './pages/Wizard';

import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
      <BrowserRouter>
        <Routes>
          <Route path={'/'} element={<App/>}>
            <Route path={''} element={<Home/>}/>
            <Route path={'about'} element={<About/>}/>
            <Route path={'dashboard'} element={<Dashboard/>}/>
            <Route path={'features'} element={<Features/>}/>
            <Route path={'guide'} element={<Guide/>}/>
            <Route path={'profile'} element={<Profile/>}/>
            <Route path={'wizard'} element={<Wizard/>}/>
          </Route>
        </Routes>
      </BrowserRouter>
    </React.StrictMode>,
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
