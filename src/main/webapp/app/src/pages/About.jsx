import Typography from "@mui/material/Typography";
import * as React from "react";
import Header from "../components/NavBar";
import Footer from "../components/Footer";
import CenterWrapper from "../components/CenterWrapper";

const About = () => {
  return (
    <>
      <Header />
      <Footer />
      <CenterWrapper>
        <Typography variant="h2" component="div" textAlign="center">
          About
        </Typography>
      </CenterWrapper>
    </>
  );
};

export default About;
