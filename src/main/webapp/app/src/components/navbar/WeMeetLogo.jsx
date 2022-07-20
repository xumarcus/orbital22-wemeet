import logo from './logo_banner.png'
import Box from '@mui/material/Box'
import * as React from 'react'

const WeMeetLogo = () => (
  <Box
    component='img'
    sx={{
      display: { xs: 'none', md: 'flex' },
      my: 3,
      mx: 2,
      maxHeight: { xs: 320, md: 250 },
      maxWidth: { xs: 350, md: 300 }
    }}
    alt='WeMeet'
    src={logo}
  />
)

export default WeMeetLogo