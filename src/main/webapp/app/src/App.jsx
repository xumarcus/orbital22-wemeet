import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import NavigationBar from "./components/NavigationBar";
import { Outlet } from "react-router-dom";

const App = () => {
    return (
        <React.Fragment>
            <CssBaseline />
            <NavigationBar />
            <Outlet />
        </React.Fragment>
    );
};

export default App;
