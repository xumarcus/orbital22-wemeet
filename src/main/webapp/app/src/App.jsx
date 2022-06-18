import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import {Outlet} from 'react-router-dom';
import Footer from './components/Footer';
import NavBar from './components/NavBar';
import Container from '@mui/material/Container';

const App = () => {
  return (
      <>
        <CssBaseline/>
        <NavBar/>
        <Container maxWidth={'xl'}>
          <Outlet/>
        </Container>
        <Footer/>
      </>
  );
};

export default App;
