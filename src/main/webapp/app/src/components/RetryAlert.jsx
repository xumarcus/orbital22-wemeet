import React from 'react'
import Alert from '@mui/material/Alert'
import { ERROR_MESSAGES } from '../core/const'

const RetryAlert = () => {
  return <Alert severity='error'>{ERROR_MESSAGES.DEFAULT_MESSAGE}</Alert>
}

export default RetryAlert
