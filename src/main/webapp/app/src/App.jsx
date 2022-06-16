import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import {Outlet} from 'react-router-dom';
import Footer from './components/Footer';
import NavBar from './components/NavBar';

const App = () => {
  return (
      <>
        <CssBaseline/>
        <NavBar/>
        <React.Fragment>
          <Outlet/>
        </React.Fragment>
        <Footer/>
      </>
  );
};

export default App;
