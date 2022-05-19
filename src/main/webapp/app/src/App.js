import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import NavigationBar from "./components/NavigationBar";
import Typography from "@mui/material/Typography";
import { Grid } from "@mui/material";
import UserProfileExample from "./components/UserProfileExample";

const App = () => {
    return (
        <React.Fragment>
            <CssBaseline />
            <NavigationBar />
            <Grid
                container
                direction={"column"}
                alignItems={"center"}
                justifyContent={"center"}
                style={{ minHeight: '80vh' }}>
                <Grid item>
                    <Typography variant="h1" component="div">
                        <UserProfileExample />
                    </Typography>
                </Grid>
            </Grid>
        </React.Fragment>
    );
};

export default App;
