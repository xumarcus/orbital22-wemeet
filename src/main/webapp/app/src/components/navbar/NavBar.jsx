import MenuIcon from '@mui/icons-material/Menu'
import AppBar from '@mui/material/AppBar'
import Avatar from '@mui/material/Avatar'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import Container from '@mui/material/Container'
import IconButton from '@mui/material/IconButton'
import Menu from '@mui/material/Menu'
import MenuItem from '@mui/material/MenuItem'
import Toolbar from '@mui/material/Toolbar'
import Tooltip from '@mui/material/Tooltip'
import Typography from '@mui/material/Typography'
import * as React from 'react'
import { useContext, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import LogInModal from './authentication/LogInModal'
import SignUpModal from './authentication/SignUpModal'
import AppContext, { defaultAppContext } from '../../core/AppContext'
import ajax from '../../core/ajax'
import WeMeetLogo from './WeMeetLogo'
import MaterialLink from '@mui/material/Link'

/*
const pages = [
  ['Features', 'features'],
  ['Guide', 'guide'],
  ['About Us', 'about']
]

 */

const settings = [
  ['Dashboard', 'dashboard'],
  // TODO ['Profile', 'profile'],
  ['Log out', 'logout']
]

const NavBar = () => {
  const { context, setContext } = useContext(AppContext)

  const [anchorElNav, setAnchorElNav] = useState(null)
  const [anchorElUser, setAnchorElUser] = useState(null)
  const [ModalVisible, setModalVisible] = useState('')

  const navigate = useNavigate()

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget)
  }
  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget)
  }

  const handleCloseNavMenu = (newPage) => {
    setAnchorElNav(null)
    navigate(`/${newPage}`)
  }

  const handleCloseUserMenu = async (newPage) => {
    setAnchorElUser(null)
    if (newPage === 'logout') {
      try {
        await ajax('POST')('/logout')
      } catch (e) {
        // do nothing
      } finally {
        setContext(defaultAppContext)
      }
    } else {
      navigate(`/${newPage}`)
    }
  }

  const handleStartNowClick = () => {
    setModalVisible('signin')
  }

  return (
    <>
      <LogInModal visible={ModalVisible} setVisible={setModalVisible} />
      {/* TODO add forget password support
      <ForgetPasswordModel
        visible={ModalVisible}
        setVisible={setModalVisible}
      />
      */}
      <SignUpModal visible={ModalVisible} setVisible={setModalVisible} />
      <AppBar position='static' sx={{ bgcolor: 'white', boxShadow: '0' }}>
        <Container maxWidth='xl'>
          <Toolbar disableGutters>
            <Link to='/'>
              <WeMeetLogo />
            </Link>
            <Box sx={{ flexGrow: 1, display: { xs: 'flex', md: 'none' } }}>
              <IconButton
                size='large'
                aria-label='account of current user'
                aria-controls='menu-appbar'
                aria-haspopup='true'
                onClick={handleOpenNavMenu}
                color='secondary'
              >
                <MenuIcon />
              </IconButton>
              <Menu
                id='menu-appbar'
                anchorEl={anchorElNav}
                anchorOrigin={{
                  vertical: 'bottom',
                  horizontal: 'left'
                }}
                keepMounted
                transformOrigin={{
                  vertical: 'top',
                  horizontal: 'left'
                }}
                open={Boolean(anchorElNav)}
                onClose={handleCloseNavMenu}
                sx={{
                  display: { xs: 'block', md: 'none' }
                }}
              >
                {/* FIXME comment out
                pages.map((page) => (
                  <MenuItem
                    key={page[1]}
                    onClick={handleCloseNavMenu.bind(this, page[1])}
                  >
                    <Typography textAlign='center'>{page[0]}</Typography>
                  </MenuItem>
                )) */}
                <MenuItem>
                  <MaterialLink href='/static/docs/index.html'>Guide</MaterialLink>
                </MenuItem>
              </Menu>
            </Box>
            <Box
              sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' }, pr: 2 }}
              justifyContent='flex-end'
            >
              {/* FIXME comment out
              pages.map((page) => (
                <Button
                  key={page}
                  onClick={handleCloseNavMenu.bind(this, page[1])}
                  sx={{
                    my: 2,
                    color: 'black',
                    fontWeight: 'bold',
                    display: 'block'
                  }}
                >
                  {page[1]}
                </Button>
              )) */}
              <Button
                sx={{
                  my: 2,
                  color: 'black',
                  fontWeight: 'bold',
                  display: 'block'
                }}
                href='/static/docs/index.html'
              >Guide
              </Button>
            </Box>

            <Box sx={{ flexGrow: 0 }}>
              <Tooltip title='Open settings'>
                <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                  {/* TODO add avatar support */}
                  <Avatar
                    alt={context.user?._links.self.href ?? 'Anonymous'}
                    src='/avatar-does-not-exist'
                  />
                </IconButton>
              </Tooltip>
              <Menu
                sx={{ mt: '45px' }}
                id='menu-appbar'
                anchorEl={anchorElUser}
                anchorOrigin={{
                  vertical: 'top',
                  horizontal: 'right'
                }}
                keepMounted
                transformOrigin={{
                  vertical: 'top',
                  horizontal: 'right'
                }}
                open={Boolean(anchorElUser)}
                onClose={() => setAnchorElUser(null)}
              >
                {settings.map((setting) => (
                  <MenuItem
                    key={setting[0]}
                    onClick={handleCloseUserMenu.bind(this,
                      setting[1])}
                  >
                    <Typography textAlign='center'>{setting[0]}</Typography>
                  </MenuItem>
                ))}
              </Menu>
            </Box>
            <Box sx={{ pl: 2 }}>
              <Button
                variant='contained'
                size='large'
                onClick={handleStartNowClick}
                color='success'
              >
                Start Now
              </Button>
            </Box>
          </Toolbar>
        </Container>
      </AppBar>
    </>
  )
}
export default NavBar
