import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import { Outlet } from "react-router-dom";
import Footer from "./components/Footer";
import Home from "./pages/Home";
import NavigationBar from "./components/NavBar";

const App = () => {
    return (
        <React.Fragment>
            <CssBaseline />
            <NavigationBar />
            <Home />
            <Footer />
            <Outlet />
        </React.Fragment>
    );
};

export default App;
