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
        <Typography align='center' variant='h3'>
          Scheduling <br />
          Made <strong>Easy</strong>
        </Typography>
      </Box>
      <Box sx={{ m: 5 }}>
        <Typography variant='body'>
          WeMeet is an orbital project meant to fulfil the need for a more
          personalised scheduling experience. Offering a unique "Rank" based
          system which results in more fine tuned results.
        </Typography>
      </Box>
    </Grid>
    <Grid item md={6}>
      <Box sx={{ width: '100%' }} component='img' src='cover_img.png' />
    </Grid>
    <Grid item md={4}>
      <Typography align='center' variant='h5'>
        Why use WeMeet?
      </Typography>
      <Box sx={{ m: 5 }}>
        <Typography variant='body'>
          Unlike existing solutions that offer binary selection of available,
          WeMeet aims to provide a better solution through the use of ranked
          selections. The user interface is also simple and easy to use.
        </Typography>
      </Box>
    </Grid>
    <Grid item md={4}>
      <Typography align='center' variant='h5'>
        What can WeMeet Do?
      </Typography>
      <Box sx={{ m: 5 }}>
        <Typography variant='body'>
          Create a schedule for your team, or for yourself. Plan meetups with
          friends and family. The options are endless.
        </Typography>
      </Box>
    </Grid>
    <Grid item md={4}>
      <Typography align='center' variant='h5'>
        Our Technology
      </Typography>
      <Box sx={{ m: 5 }}>
        <Typography variant='body'>
          WeMeet is hosted on Heroku and is built with React, Material-UI, and
          the Spring framework. Processing is done using the powerful
          OptaPlanner AI.
        </Typography>
      </Box>
    </Grid>
  </Grid>
)

export default Home
