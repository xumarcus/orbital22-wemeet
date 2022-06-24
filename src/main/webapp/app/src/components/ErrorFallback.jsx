import Typography from '@mui/material/Typography'
import * as React from 'react'
import CenterWrapper from './CenterWrapper'

const ErrorFallback = ({ error }) => {
  return (
    <CenterWrapper>
      <Typography variant='h1' align='center'>
        ⚠
      </Typography>
      <Typography variant='h5' align='center'>
        {error?.message || 'An error has occurred.'}
      </Typography>
    </CenterWrapper>
  )
}

export default ErrorFallback
