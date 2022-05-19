import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';

// TODO
const HomeIcon = () => (
    <IconButton
        size="large"
        edge="start"
        color="inherit"
        aria-label="menu"
        sx={{ mr: 2 }}
    >
        <MenuIcon />
    </IconButton>
);

const NavigationBar = () => {
    // Do not use React router... yet
    const handleClick = () => {
        window.location.href = '/login';
    };

    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static">
                <Toolbar>
                    <HomeIcon />
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        WeMeet
                    </Typography>
                    <Button onClick={handleClick} color="inherit">Login</Button>
                </Toolbar>
            </AppBar>
        </Box>
    );
}
export default NavigationBar;