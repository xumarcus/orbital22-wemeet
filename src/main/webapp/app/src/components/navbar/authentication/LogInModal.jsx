import * as React from 'react'
import { useContext } from 'react'
import Backdrop from '@mui/material/Backdrop'
import Box from '@mui/material/Box'
import Modal from '@mui/material/Modal'
import Fade from '@mui/material/Fade'
import Button from '@mui/material/Button'
import Typography from '@mui/material/Typography'
import Avatar from '@mui/material/Avatar'
import CssBaseline from '@mui/material/CssBaseline'
import TextField from '@mui/material/TextField'
import FormControlLabel from '@mui/material/FormControlLabel'
import Checkbox from '@mui/material/Checkbox'
import Link from '@mui/material/Link'
import Grid from '@mui/material/Grid'
import LockOutlinedIcon from '@mui/icons-material/LockOutlined'
import Container from '@mui/material/Container'
import RetryAlert from './RetryAlert'
import ajax from '../../../core/ajax'
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

const LogInModal = ({ visible, setVisible }) => {
  const navigate = useNavigate()
  const { context, setContext } = useContext(AppContext)

  const handleClose = () => {
    setRetryAlert(false)
    setVisible('')
  }

  const [retryAlert, setRetryAlert] = React.useState(false)

  const handleSwitchtoForgetPassword = () => {
    setVisible('forgetPassword')
  }

  const handleSwitchtoSignUp = () => {
    setVisible('signup')
  }

  const handleSubmit = async (event) => {
    event.preventDefault()
    const formData = new window.FormData(event.currentTarget)
    try {
      const user = await ajax('POST', formData)('/login')
      setContext({ ...context, user })
      handleClose()
      navigate('/dashboard')
    } catch (e) {
      console.log(e) // FIXME
      setRetryAlert(true)
    }
  }

  return (
    <Modal
      aria-labelledby='transition-modal-title'
      open={visible === 'signin'}
      onClose={handleClose}
      // closeAfterTransition
      BackdropComponent={Backdrop}
      BackdropProps={{
        timeout: 500
      }}
      sx={{ width: '70%', left: '15%' }}
    >
      <Fade in={visible === 'signin'}>
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
              <Typography
                id='transition-modal-title'
                component='h1'
                variant='h5'
              >
                Sign in
              </Typography>
              <Box
                component='form'
                onSubmit={handleSubmit}
                noValidate
                sx={{ mt: 1 }}
              >
                <TextField
                  margin='normal'
                  required
                  fullWidth
                  id='email'
                  label='Email Address'
                  name='email'
                  autoComplete='email'
                  autoFocus
                />
                <TextField
                  margin='normal'
                  required
                  fullWidth
                  name='password'
                  label='Password'
                  type='password'
                  id='password'
                  autoComplete='current-password'
                />
                <FormControlLabel
                  control={<Checkbox value='remember' color='primary' />}
                  label='Remember me'
                />
                <Button
                  type='submit'
                  fullWidth
                  variant='contained'
                  sx={{ mt: 2, mb: 2 }}
                >
                  Sign In
                </Button>
                <Grid container>
                  <Grid item xs>
                    <Link
                      variant='body2'
                      onClick={() => handleSwitchtoForgetPassword()}
                    >
                      Forgot password?
                    </Link>
                  </Grid>
                  <Grid item>
                    <Link
                      variant='body2'
                      onClick={() => handleSwitchtoSignUp()}
                    >
                      {'Don\'t have an account? Sign Up'}
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

export default LogInModal
