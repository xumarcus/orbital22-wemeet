import * as React from "react";
import CssBaseline from "@mui/material/CssBaseline";
import NavigationBar from "./components/NavBar";
import { Outlet } from "react-router-dom";
import Footer from "./components/Footer";
import Home from "./pages/Home";

const App = () => {
  return (
    <React.Fragment>
      <CssBaseline />
      <NavigationBar />
      <Outlet />
      <Home />
      <Footer />
    </React.Fragment>
  );
};

export default App;
