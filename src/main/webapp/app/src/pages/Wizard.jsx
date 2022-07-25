import Typography from "@mui/material/Typography";
import * as React from "react";
import { useState } from "react";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import Header from "../components/NavBar";
import Footer from "../components/Footer";
import Grid from "@mui/material/Grid";
import Stack from "@mui/material/Stack";
import Button from "@mui/material/Button";
import LoadingButton from "@mui/lab/LoadingButton";

const Wizard = () => {
    const [rosterBtnLoading, setRosterBtnLoading] = useState(false);

    const handleClick = () => {
        setRosterBtnLoading(true);
    };

    return (
        <>
            <Header />
            <Footer />
            <Grid
                container
                direction="column"
                justifyContent="flex-start"
                alignItems="center"
                border="1px dashed"
                sx={{
                    bgcolor: "#E3FFFC",
                    boxShadow: "4",
                    mt: "20px",
                    width: "90%",
                    ml: "5%",
                    alignSelf: "center",
                    pt: 5
                }}
            >
                <Grid item>
                    <Typography
                        variant="h4"
                        component="div"
                        sx={{ border: "1px dashed", bm: 2 }}
                    >
                        I would like to:
                    </Typography>
                </Grid>
                <Grid item sx={{ border: "1px dashed", my: 5, width: "85%" }}>
                    <Stack spacing={3} sx={{ border: "5px dashed" }}>
                        <LoadingButton
                            variant="contained"
                            size="large"
                            loading={rosterBtnLoading}
                            onClick={handleClick}
                        >
                            Generate a Roster
                        </LoadingButton>
                        <Typography variant="body1" align="center" fontWeight="bold">
                            Upcoming Features
                        </Typography>
                        <Button variant="contained" size="large" disabled>
                            Schedule a One-on-One Meetup{" "}
                        </Button>
                        <Button variant="contained" size="large" disabled>
                            Schedule a Group Meetup
                        </Button>
                    </Stack>
                </Grid>
            </Grid>
        </>
    );
};

export default Wizard;