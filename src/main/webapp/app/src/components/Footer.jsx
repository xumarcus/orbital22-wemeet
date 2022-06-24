import Toolbar from '@mui/material/Toolbar'
import Typography from '@mui/material/Typography'
import * as React from 'react'

const Footer = () => {
  return (
    <Toolbar sx={{
      display: 'flex',
      justifyContent: 'center',
      bgcolor: '#e3e3e3',
      color: 'black',
      mt: 5
    }}
    >
      <Typography align='center'>An Orbital 2022 Project</Typography>
    </Toolbar>
  )
}

export default Footer
