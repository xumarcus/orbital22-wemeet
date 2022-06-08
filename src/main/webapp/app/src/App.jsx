import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import {BrowserRouter, Outlet, Route, Routes} from "react-router-dom";
import Footer from "./components/Footer";
import Home from "./pages/Home";
import NavigationBar from "./components/NavBar";
import {UserContext} from "./UserContext";
import Features from "./pages/Features";
import Guide from "./pages/Guide";
import About from "./pages/About";
import Wizard from "./pages/Wizard";
import Dashboard from "./pages/Dashboard";
import Profile from "./pages/Profile";
import Meetings from "./pages/Meetings";
import Roster from "./pages/Roster";

const App = () => {
    const [user, setUser] = React.useState(null);


    // return (
    //     <React.Fragment>
    //         <CssBaseline/>
    //         <NavigationBar/>
    //         <Home/>
    //         <Footer/>
    //         <Outlet/>
    //     </React.Fragment>
    // );
    return <>
            <BrowserRouter>
                <UserContext.Provider value={{user, setUser}}>
                    <Routes>
                        <Route path={ "/" } element={ <Home/> }/>
                        <Route path={ "/features" } element={ <Features/> }/>
                        <Route path={ "/guide" } element={ <Guide/> }/>
                        <Route path={ "/about" } element={ <About/> }/>
                        <Route path={ "/wizard" } element={ <Wizard/> }/>
                        <Route path={ "/dashboard" } element={ <Dashboard/> }/>
                        <Route path={ "/profile" } element={ <Profile/> }/>
                        <Route path={ "/meeting" } element={ <Meetings/> }/>
                        <Route path={ "/roster" } element={ <Roster/> }/>
                    </Routes>
                </UserContext.Provider>
            </BrowserRouter>
    </>
};

export default App;
