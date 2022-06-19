import Typography from '@mui/material/Typography'
import * as React from 'react'
import { useContext } from 'react'
import CenterWrapper from '../components/CenterWrapper'
import AppContext from '../core/AppContext'

// TODO
const Profile = () => {
  const { context, setContext } = useContext(AppContext)

  return (
    <CenterWrapper>
      <Typography variant='h3' component='div' textAlign='center'>
        {JSON.stringify(context.user)}
      </Typography>
    </CenterWrapper>
  )
}

export default Profile
