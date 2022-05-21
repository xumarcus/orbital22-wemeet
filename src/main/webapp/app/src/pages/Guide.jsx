import Typography from "@mui/material/Typography";
import * as React from "react";
import Header from "../components/NavBar";
import Footer from "../components/Footer";
import CenterWrapper from "../components/CenterWrapper";

const Guide = () => {
  return (
    <>
      <Header />
      <Footer />
      <CenterWrapper>
        <Typography variant="h3" align="center">
          User / Developer Guide <br />
          Coming Soon{" "}
        </Typography>
      </CenterWrapper>
    </>
  );
};

export default Guide;
