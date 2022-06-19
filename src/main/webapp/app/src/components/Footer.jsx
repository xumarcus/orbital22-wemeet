import AppBar from '@mui/material/AppBar'
import Toolbar from '@mui/material/Toolbar'
import Typography from '@mui/material/Typography'
import * as React from 'react'

const Footer = () => {
  return (
    <AppBar
      sx={{
        bgcolor: '#e3e3e3',
        color: 'black',
        top: 'auto',
        bottom: 0,
        textAlign: 'center'
      }}
    >
      <Toolbar sx={{ display: 'flex', justifyContent: 'center' }}>
        <Typography align='center'>An Orbital 2022 Project</Typography>
      </Toolbar>
    </AppBar>
  )
}

export default Footer
