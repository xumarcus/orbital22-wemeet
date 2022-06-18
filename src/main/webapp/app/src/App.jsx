import * as React from 'react';
import {useState} from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import {Outlet} from 'react-router-dom';
import Footer from './components/Footer';
import NavBar from './components/NavBar';
import Container from '@mui/material/Container';
import AppContext, {defaultAppContextValues} from './core/app-context';

const App = () => {
  // FIXME values are cleared on refresh
  const [values, setValues] = useState(defaultAppContextValues);

  return (
      <>
        <CssBaseline/>
        <AppContext.Provider value={{values, setValues}}>
          <NavBar/>
          <Container maxWidth={'xl'}>
            <Outlet/>
          </Container>
        </AppContext.Provider>
        <Footer/>
      </>
  );
};

export default App;
