import * as React from 'react'
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
// import ReCAPTCHA from "react-google-recaptcha";
// need domain to use

const style = {
  position: 'absolute',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  width: 400,
  bgcolor: 'background.paper',
  border: '2px solid #000',
  boxShadow: 24,
  p: 4,
}

const ForgetPasswordModal = (prop) => {
  const { visible, setVisible } = prop
  const handleClose = () => setVisible('')

  const handleSwitchtoSignIn = () => {
    setVisible('signin')
  }

  const handleSwitchtoSignUp = () => {
    setVisible('signup')
  }

  const handleSubmit = (event) => {
    event.preventDefault()
    const data = new FormData(event.currentTarget)
    console.log({
      email: data.get('email'),
      password: data.get('password'),
    })
  }

  return (
    <Modal
      aria-labelledby="transition-modal-title"
      open={visible === 'forgetPassword'}
      onClose={handleClose}
      // closeAfterTransition
      BackdropComponent={Backdrop}
      BackdropProps={{
        timeout: 500,
      }}
      sx={{ width: '70%', left: '15%' }}
    >
      <Fade in={visible === 'forgetPassword'}>
        <Box sx={style}>
          {/* white box to hold form */}
          <Container component="main" maxWidth="xs">
            <CssBaseline/>
            <Box
              sx={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
              }}
            >
              <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
                <LockOutlinedIcon/>
              </Avatar>
              <Typography
                id="transition-modal-title"
                component="h1"
                variant="h5"
              >
                Reset Password
              </Typography>
              <Box
                component="form"
                onSubmit={handleSubmit}
                noValidate
                sx={{ mt: 1 }}
              >
                <TextField
                  margin="normal"
                  required
                  fullWidth
                  id="email"
                  label="Email Address"
                  name="email"
                  autoComplete="email"
                  autoFocus
                />

                <Button
                  type="submit"
                  fullWidth
                  variant="contained"
                  sx={{ mt: 2, mb: 2 }}
                >
                  Send Recovery Email
                </Button>
                <Grid container>
                  <Grid item xs>
                    <Link
                      variant="body2"
                      onClick={() => handleSwitchtoSignIn()}
                    >
                      Sign In
                    </Link>
                  </Grid>
                  <Grid item>
                    <Link
                      variant="body2"
                      onClick={() => handleSwitchtoSignUp()}
                    >
                      Don't have an account? Sign Up
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

export default ForgetPasswordModal
