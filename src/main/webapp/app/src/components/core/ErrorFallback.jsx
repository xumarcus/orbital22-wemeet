import Typography from '@mui/material/Typography'
import * as React from 'react'
import CenterWrapper from './CenterWrapper'
import { ERROR_MESSAGES } from '../../core/const'

const ErrorFallback = ({ error }) => {
  return (
    <CenterWrapper>
      <Typography variant='h1' align='center'>
        ⚠
      </Typography>
      <Typography variant='h5' align='center'>
        {error?.message ?? ERROR_MESSAGES.DEFAULT_MESSAGE}
      </Typography>
    </CenterWrapper>
  )
}

export default ErrorFallback
