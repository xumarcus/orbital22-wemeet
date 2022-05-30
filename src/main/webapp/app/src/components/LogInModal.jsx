import * as React from "react";
import Backdrop from "@mui/material/Backdrop";
import Box from "@mui/material/Box";
import Modal from "@mui/material/Modal";
import Fade from "@mui/material/Fade";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Avatar from "@mui/material/Avatar";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Container from "@mui/material/Container";
import SuccessAlert from "./SuccessAlert";
import RetryAlert from "./RetryAlert";
import ajax from "../util";


const style = {
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: 400,
    bgcolor: "background.paper",
    border: "2px solid #000",
    boxShadow: 24,
    p: 4
};

const LogInModal = (prop) => {
    const { visible, setVisible } = prop;
    const handleClose = () => {
        setSuccessAlert(false);
        setRetryAlert(false);
        setVisible("");
    }
    const [successAlert, setSuccessAlert] = React.useState(false);
    const [retryAlert, setRetryAlert] = React.useState(false);


    const handleSwitchtoForgetPassword = () => {
        console.log("forgetpw");
        setVisible("forgetPassword");
    };

    const handleSwitchtoSignUp = () => {
        console.log("signup");
        setVisible("signup");
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        const body = new FormData(event.currentTarget);
        const loginResponse = await fetch("/login",
            { method: "POST", body });

        // In development, resp.type === 'cors'
        // In production, resp.type is per normal
        if (loginResponse.url.endsWith("error")) {
            setRetryAlert(true);
            setSuccessAlert(false);
            return;
        }
        setRetryAlert(false);
        setSuccessAlert(true);

        const params = new URLSearchParams({
            email: body.get("email")
        });
        const user = await ajax("GET")("/api/users/search/findByEmail?" + params);

        // TODO preserve user in context
        console.log(user);
    };

    return (
        <Modal
            aria-labelledby="transition-modal-title"
            open={visible === "signin"}
            onClose={handleClose}
            // closeAfterTransition
            BackdropComponent={Backdrop}
            BackdropProps={{
                timeout: 500
            }}
            sx={{ width: "70%", left: "15%" }}
        >
            <Fade in={visible === "signin"}>
                <Box sx={style}>
                    {/* white box to hold form */}
                    {successAlert && <SuccessAlert />}
                    {retryAlert && <RetryAlert />}
                    <Container component="main" maxWidth="xs">
                        <CssBaseline />
                        <Box
                            sx={{
                                display: "flex",
                                flexDirection: "column",
                                alignItems: "center"
                            }}
                        >
                            <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
                                <LockOutlinedIcon />
                            </Avatar>
                            <Typography
                                id="transition-modal-title"
                                component="h1"
                                variant="h5"
                            >
                                Sign in
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
                                <TextField
                                    margin="normal"
                                    required
                                    fullWidth
                                    name="password"
                                    label="Password"
                                    type="password"
                                    id="password"
                                    autoComplete="current-password"
                                />
                                <FormControlLabel
                                    control={<Checkbox value="remember" color="primary" />}
                                    label="Remember me"
                                />
                                <Button
                                    type="submit"
                                    fullWidth
                                    variant="contained"
                                    sx={{ mt: 2, mb: 2 }}
                                >
                                    Sign In
                                </Button>
                                <Grid container>
                                    <Grid item xs>
                                        <Link
                                            variant="body2"
                                            onClick={() => handleSwitchtoForgetPassword()}
                                        >
                                            Forgot password?
                                        </Link>
                                    </Grid>
                                    <Grid item>
                                        <Link
                                            variant="body2"
                                            onClick={() => handleSwitchtoSignUp()}
                                        >
                                            {"Don't have an account? Sign Up"}
                                        </Link>
                                    </Grid>
                                </Grid>
                            </Box>
                        </Box>
                    </Container>
                </Box>
            </Fade>
        </Modal>
    );
};

export default LogInModal;
