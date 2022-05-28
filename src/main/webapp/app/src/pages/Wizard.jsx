import Typography from "@mui/material/Typography";
import * as React from "react";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import Header from "../components/NavBar";
import Footer from "../components/Footer";

const Wizard = () => {
  return (
    <>
      <Header />
      <Footer />
      <Container>
        <Box>
          <Typography variant="h1" component="div">
            Wizard
          </Typography>
        </Box>
      </Container>
    </>
  );
};

export default Wizard;
