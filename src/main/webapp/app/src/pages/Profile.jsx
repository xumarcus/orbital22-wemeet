import * as React from 'react'
import { useContext } from 'react'
import CenterWrapper from '../components/CenterWrapper'
import AppContext from '../core/AppContext'
import FormControl from '@mui/material/FormControl'
import { FormLabel } from '@mui/material'
import Grid from '@mui/material/Grid'
import TextField from '@mui/material/TextField'
import Button from '@mui/material/Button'

const Profile = () => {
  const { context } = useContext(AppContext)

  return (
    <CenterWrapper>
      <Grid>
        <FormControl id='firstName'>
          <FormLabel>First Name</FormLabel>
          <TextField id='outlined' defaultValue='John' />
        </FormControl>
        <FormControl id='lastName'>
          <FormLabel>Last Name</FormLabel>
          <TextField id='outlined' defaultValue='Doe' />
        </FormControl>
        <FormControl id='emailAddress'>
          <FormLabel>Email Address</FormLabel>
          <TextField id='outlined' type='email'>
            {context.user.email}
          </TextField>
        </FormControl>
        <Button variant='contained'>Update</Button>
      </Grid>
    </CenterWrapper>
  )
}

export default Profile
