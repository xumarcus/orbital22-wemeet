import MenuIcon from "@mui/icons-material/Menu";
import AppBar from "@mui/material/AppBar";
import Avatar from "@mui/material/Avatar";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import IconButton from "@mui/material/IconButton";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import Toolbar from "@mui/material/Toolbar";
import Tooltip from "@mui/material/Tooltip";
import Typography from "@mui/material/Typography";
import * as React from "react";
import { useState } from "react";
import logo from "./logo2.png";
import { Link, useNavigate } from "react-router-dom";
import LogInModal from "./LogInModal";
import ForgetPasswordModel from "./ForgetPasswordModal";
import SignUpModal from "./SignUpModal";

const pages = [
  // ["Display Name", "url"]
  ["Features", "features"],
  ["Guide", "guide"],
  ["About Us", "about"]
];

const settings = [
  ["Dashboard", "dashboard"],
  ["Profile", "profile"],
];

const ResponsiveAppBar = () => {
  const [anchorElNav, setAnchorElNav] = useState(null);
  const [anchorElUser, setAnchorElUser] = useState(null);
  const [ModalVisible, setModalVisible] = useState("");

  let navigate = useNavigate();

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = (newPage) => {
    setAnchorElNav(null);
    // console.log(e.currentTarget.outerText);
    navigate(`/${newPage}`);
  };

  const handleCloseUserMenu = (newPage) => {
    setAnchorElUser(null);
    navigate(`/${ newPage }`);
  };

  const handleStartNowClick = () => {
    console.log("Start Now");
    setModalVisible("signin");
  };

  return (
    <>
      <LogInModal visible={ModalVisible} setVisible={setModalVisible} />
      <ForgetPasswordModel
          visible={ ModalVisible }
          setVisible={ setModalVisible }
      />
      <SignUpModal visible={ ModalVisible } setVisible={ setModalVisible }/>
      <AppBar position="static" sx={ { bgcolor: "white", boxShadow: "0" } }>
        <Container maxWidth="xl">
          <Toolbar disableGutters>
            <Link to={ "/" }>
              <Box
                  component="img"
                  sx={ {
                    display: { xs: "none", md: "flex" },
                    my: 3,
                    mx: 2,
                  maxHeight: { xs: 320, md: 250 },
                  maxWidth: { xs: 350, md: 300 }
                }}
                alt="WeMeet"
                src={logo}
              />
            </Link>
            <Box sx={{ flexGrow: 1, display: { xs: "flex", md: "none" } }}>
              <IconButton
                size="large"
                aria-label="account of current user"
                aria-controls="menu-appbar"
                aria-haspopup="true"
                onClick={handleOpenNavMenu}
                color="secondary"
              >
                <MenuIcon />
              </IconButton>
              <Menu
                id="menu-appbar"
                anchorEl={anchorElNav}
                anchorOrigin={{
                  vertical: "bottom",
                  horizontal: "left"
                }}
                keepMounted
                transformOrigin={{
                  vertical: "top",
                  horizontal: "left"
                }}
                open={Boolean(anchorElNav)}
                onClose={handleCloseNavMenu}
                sx={{
                  display: { xs: "block", md: "none" }
                }}
              >
                {pages.map((page) => (
                  <MenuItem
                    key={page}
                    onClick={handleCloseNavMenu.bind(this, page[1])}
                  >
                    <Typography textAlign="center">{page[0]}</Typography>
                  </MenuItem>
                ))}
              </Menu>
            </Box>
            <Box
              sx={{ flexGrow: 1, display: { xs: "none", md: "flex" }, pr: 2 }}
              justifyContent="flex-end"
            >
              {pages.map((page) => (
                <Button
                  key={page}
                  onClick={handleCloseNavMenu.bind(this, page[1])}
                  sx={{
                    my: 2,
                    color: "black",
                    fontWeight: "bold",
                    display: "block"
                  }}
                >
                  {page[1]}
                </Button>
              ))}
            </Box>

            <Box sx={{ flexGrow: 0 }}>
              <Tooltip title="Open settings">
                <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                  <Avatar alt="Remy Sharp" src="/static/images/avatar/2.jpg" />
                </IconButton>
              </Tooltip>
              <Menu
                sx={{ mt: "45px" }}
                id="menu-appbar"
                anchorEl={anchorElUser}
                anchorOrigin={{
                  vertical: "top",
                  horizontal: "right"
                }}
                keepMounted
                transformOrigin={{
                  vertical: "top",
                  horizontal: "right"
                }}
                open={Boolean(anchorElUser)}
                onClose={handleCloseUserMenu}
              >
                {settings.map((setting) => (
                    <MenuItem key={ setting[0] } onClick={ handleCloseUserMenu.bind(this, setting[1]) }>
                      <Typography textAlign="center">{ setting[0] }</Typography>
                    </MenuItem>
                ))}
              </Menu>
            </Box>
            <Box sx={{ pl: 2 }}>
              <Button
                variant="contained"
                size="large"
                onClick={handleStartNowClick}
                color="success"
              >
                Start Now
              </Button>
            </Box>
          </Toolbar>
        </Container>
      </AppBar>
    </>
  );
};
export default ResponsiveAppBar;