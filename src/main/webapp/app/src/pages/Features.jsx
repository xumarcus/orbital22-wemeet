import * as React from 'react'
import Box from "@mui/material/Box";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";

const Features = () => {
    return (
        <Container
            sx ={{
                display: 'flex',
                flexDirection: 'column',
                width: '100%',
                justifyContent: "center",
                alignContent: 'center',
                alignItems: 'center',
                mt: 5
        }}
        >
            <Box
                component="img"
                sx = {{
                    width: '85%'
                }}
                alt="Poster"
                src='./images/poster.jpg'/>
            <Typography variant='body' sx={{mt: 4}}>
                Updated as of Milestone 2
            </Typography>
        </Container>
    )
}

export default Features