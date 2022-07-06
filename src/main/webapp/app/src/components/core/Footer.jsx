import Toolbar from '@mui/material/Toolbar'
import Typography from '@mui/material/Typography'
import * as React from 'react'
import { SYSTEM_THEME, TEXT } from '../../core/const'

const Footer = () => {
  return (
    <Toolbar sx={{
      display: 'flex',
      justifyContent: 'center',
      bgcolor: SYSTEM_THEME.palette.grey['500'],
      color: 'black',
      mt: 5
    }}
    >
      <Typography align='center'>{TEXT.FOOTER}</Typography>
    </Toolbar>
  )
}

export default Footer
