import Typography from '@mui/material/Typography'
import * as React from 'react'
import Box from '@mui/material/Box'
import Grid from '@mui/material/Grid'

// TODO make this responsive
const Home = () => (
  <Grid
    container
    spacing={5}
  >
    <Grid item md={6}>
      <Box sx={{ mt: 15 }}>
        <Typography align="center" variant="h3">
          Scheduling <br/>
          Made <strong>Easy</strong>
        </Typography>
      </Box>
      <Box sx={{ m: 5 }}>
        <Typography variant="body">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
          do eiusmod tempor incididunt ut labore et dolore magna aliqua.
          Ut enim ad minim veniam, quis nostrud exercitation ullamco
          laboris nisi ut aliquip ex ea commodo consequat.
        </Typography>
      </Box>
    </Grid>
    <Grid item md={6}>
      <Box sx={{ width: '100%' }} component="img" src="cover_img.png"/>
    </Grid>
    <Grid item md={4}>
      <Typography align="center" variant="h5">
        Item 1?
      </Typography>
      <Box sx={{ m: 5 }}>
        <Typography variant="body">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
          do eiusmod tempor incididunt ut labore et dolore magna aliqua.
          Ut enim ad minim veniam, quis nostrud exercitation ullamco
          laboris nisi ut aliquip ex ea commodo consequat.
        </Typography>
      </Box>
    </Grid>
    <Grid item md={4}>
      <Typography align="center" variant="h5">
        Item 2?
      </Typography>
      <Box sx={{ m: 5 }}>
        <Typography variant="body">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
          do eiusmod tempor incididunt ut labore et dolore magna aliqua.
          Ut enim ad minim veniam, quis nostrud exercitation ullamco
          laboris nisi ut aliquip ex ea commodo consequat.
        </Typography>
      </Box>
    </Grid>
    <Grid item md={4}>
      <Typography align="center" variant="h5">
        Item 3?
      </Typography>
      <Box sx={{ m: 5 }}>
        <Typography variant="body">
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed
          do eiusmod tempor incididunt ut labore et dolore magna aliqua.
          Ut enim ad minim veniam, quis nostrud exercitation ullamco
          laboris nisi ut aliquip ex ea commodo consequat.
        </Typography>
      </Box>
    </Grid>
  </Grid>
)

export default Home
