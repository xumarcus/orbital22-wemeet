import Typography from '@mui/material/Typography'
import * as React from 'react'
import { useContext } from 'react'
import CenterWrapper from '../components/CenterWrapper'
import AppContext from '../core/AppContext'
import FormControl from '@mui/material/FormControl'
import { FormLabel, Input } from '@mui/material'
import Grid from '@mui/material/Grid'
import TextField from '@mui/material/TextField'
import Button from '@mui/material/Button'

// TODO
const Profile = () => {
  const { context, setContext } = useContext(AppContext)

  return (
    <CenterWrapper>
      <Typography variant='h3' component='div' textAlign='center'>
        {JSON.stringify(context.user)}
      </Typography>
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
