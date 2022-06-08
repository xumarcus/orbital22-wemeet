import Typography from "@mui/material/Typography";
import * as React from "react";
import Header from "../components/NavBar";
import Footer from "../components/Footer";
import CenterWrapper from "../components/CenterWrapper";

import { UserContext } from "../UserContext";

const Dashboard = () => {

    const [user, setUser] = React.useContext(UserContext);

    return (
        <>
            <Header/>
            <Footer/>
            <CenterWrapper>
                <Typography variant="h3" component="div" textAlign="center">
                    Dashboard
                    {user}
                </Typography>
            </CenterWrapper>
        </>
    );
};

export default Dashboard;