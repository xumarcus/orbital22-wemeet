import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import { Link } from "react-router-dom";
import { Button } from "@mui/material";

// TODO
const HomeIcon = () => (
    <IconButton
        size="large"
        edge="start"
        color="inherit"
        aria-label="menu"
        sx={{ mr: 2 }}
    >
        <MenuIcon/>
    </IconButton>
);

const pages = ['Features', 'Guide', 'About'];

const NavigationBar = () => {
    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position={"static"}>
                <Toolbar>
                    <HomeIcon/>
                    <Box sx={{ flexGrow: 1 }}/>
                    {pages.map(page => (
                        <Button
                            key={page}
                            color={"inherit"}
                            component={Link}
                            to={`/${page.toLowerCase()}`}
                        >
                            {page}
                        </Button>
                    ))}
                    <Button
                        color={"inherit"}
                        href={"/login"}
                    >
                        Login
                    </Button>
                </Toolbar>
            </AppBar>
        </Box>
    );
}
export default NavigationBar;