import * as React from 'react'
import { useContext, useState } from 'react'
import Backdrop from '@mui/material/Backdrop'
import Box from '@mui/material/Box'
import Modal from '@mui/material/Modal'
import Fade from '@mui/material/Fade'
import Button from '@mui/material/Button'
import Typography from '@mui/material/Typography'
import Avatar from '@mui/material/Avatar'
import CssBaseline from '@mui/material/CssBaseline'
import TextField from '@mui/material/TextField'
import Link from '@mui/material/Link'
import Grid from '@mui/material/Grid'
import LockOutlinedIcon from '@mui/icons-material/LockOutlined'
import Container from '@mui/material/Container'
import ajax from '../../../core/ajax'
import RetryAlert from './RetryAlert'

import AppContext from '../../../core/AppContext'
import { useNavigate } from 'react-router-dom'

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4
}

const SignUpModal = ({ visible, setVisible }) => {
  const navigate = useNavigate()
  const { context, setContext } = useContext(AppContext)

  const [retryAlert, setRetryAlert] = useState(false)
  const handleClose = () => {
    setRetryAlert(false)
    setVisible('')
  }

  const handleSubmit = async (event) => {
    event.preventDefault()
    const formData = new window.FormData(event.currentTarget)
    const request = {
      email: formData.get('email'),
      rawPassword: formData.get('password')
    }
    try {
      const user = await ajax('POST', request)('/api/users')
      setContext({ ...context, user })
      handleClose()
      navigate('/dashboard')
    } catch (e) {
      console.log(e.cause) // e.g. {errors: [{defaultMessage}], ...}
      setRetryAlert(true)
    }
  }

  const handleSwitchtoSignIn = () => {
    setVisible('signin')
  }

  return (
    <Modal
      aria-labelledby='transition-modal-title'
      open={visible === 'signup'}
      onClose={handleClose}
      // closeAfterTransition
      BackdropComponent={Backdrop}
      BackdropProps={{
        timeout: 500
      }}
      sx={{ width: '70%', left: '15%' }}
    >
      <Fade in={visible === 'signup'}>
        <Box sx={style}>
          {retryAlert && <RetryAlert />}
          <Container component='main' maxWidth='xs'>
            <CssBaseline />
            <Box
              sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center'
              }}
            >
              <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                <LockOutlinedIcon />
              </Avatar>
              <Typography component='h1' variant='h5'>
                Sign up
              </Typography>
              <Box
                component='form'
                noValidate
                onSubmit={handleSubmit}
                sx={{ mt: 3 }}
              >
                <Grid container spacing={2}>
                  {/* TODO add first/last names support
                  <Grid item xs={12} sm={6}>
                    <TextField
                      autoComplete='given-name'
                      name='firstName'
                      required
                      fullWidth
                      id='firstName'
                      label='First Name'
                      autoFocus
                    />
                  </Grid>
                  <Grid item xs={12} sm={6}>
                    <TextField
                      required
                      fullWidth
                      id='lastName'
                      label='Last Name'
                      name='lastName'
                      autoComplete='family-name'
                    />
                  </Grid>
                  */}
                  <Grid item xs={12}>
                    <TextField
                      required
                      fullWidth
                      id='email'
                      label='Email Address'
                      name='email'
                      autoComplete='email'
                    />
                  </Grid>
                  <Grid item xs={12}>
                    <TextField
                      required
                      fullWidth
                      name='password'
                      label='Password'
                      type='password'
                      id='password'
                      autoComplete='new-password'
                    />
                  </Grid>
                </Grid>
                <Button
                  type='submit'
                  fullWidth
                  variant='contained'
                  sx={{ mt: 3, mb: 2 }}
                >
                  Sign Up
                </Button>
                <Grid container justifyContent='flex-end'>
                  <Grid item>
                    <Link
                      variant='body2'
                      onClick={() => handleSwitchtoSignIn()}
                    >
                      Already have an account? Sign in
                    </Link>
                  </Grid>
                </Grid>
              </Box>
            </Box>
          </Container>
        </Box>
      </Fade>
    </Modal>
  )
}

export default SignUpModal
