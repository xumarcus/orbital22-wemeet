import Typography from '@mui/material/Typography'
import * as React from 'react'
import CenterWrapper from '../components/CenterWrapper'

const Error = ({ status_code, message }) => {
  return (
    <CenterWrapper>
      <Typography variant="h1" align="center">
        {status_code || 404}
      </Typography>
      <Typography variant="h5" align="center">
        {message || 'An error has occurred.'}
      </Typography>
    </CenterWrapper>
  )
}

export default Error
