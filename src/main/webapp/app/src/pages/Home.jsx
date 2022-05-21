import Typography from "@mui/material/Typography";
import * as React from "react";
import { useRef } from "react";
import Stack from "@mui/material/Stack";
import Box from "@mui/material/Box";
import CoverImg from "./cover_img.jpg";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Grid from "@mui/material/Grid";

const Home = () => {
  const handleSignUpButton = () => {
    console.log(textFieldRef.current.value || "no value");
    textFieldRef.current.value = "";
  };

  const textFieldRef = useRef();

  return (
    <>
      <Stack
        direction="row"
        justifyContent="center"
        alignItems="center"
        spacing={2}
      >
        <Box textAlign="center" width="50%">
          <Stack direction="column" justifyContent="center" alignItems="center">
            <Box sx={{ width: "100%", height: "33.3%", my: 5 }}>
              <Typography variant="h5">
                Scheduling <br />
                Made
                <br /> Easy
              </Typography>
            </Box>
            <Box sx={{ width: "100%", height: "33.3%", my: 5 }}>
              <Typography variant="body1">(some text here)</Typography>
            </Box>
            <Box sx={{ width: "100%", height: "33.3%", my: 10 }}>
              <Grid container justifyContent="center">
                <Grid item>
                  <TextField
                    inputRef={textFieldRef}
                    label="Email"
                    variant="outlined"
                  />
                </Grid>

                <Grid item alignItems="stretch" style={{ display: "flex" }}>
                  <Button
                    color="primary"
                    variant="contained"
                    onClick={handleSignUpButton}
                  >
                    Sign Up
                  </Button>
                </Grid>
              </Grid>
              <Typography variant="body2" sx={{ mt: 2 }}>
                Successfully Submitted
              </Typography>
            </Box>
          </Stack>
        </Box>
        <Box
          component="img"
          sx={{
            textAlign: "center",
            width: "50%"
          }}
          alt="The house from the p."
          src={CoverImg}
        />
      </Stack>
    </>
  );
};

export default Home;
