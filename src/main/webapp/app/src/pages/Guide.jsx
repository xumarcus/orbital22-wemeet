import Typography from "@mui/material/Typography";
import * as React from "react";
import Header from "../components/NavBar";
import Footer from "../components/Footer";
import CenterWrapper from "../components/CenterWrapper";
import UserGuide from "./UserGuide";
import DevGuide from "./DevGuide";

const Guide = () => {
    const [userGuideVisible, setUserGuideVisible] = React.useState(true);
    const devGuideVisible = !userGuideVisible;

  return (
    <>
      <Header />
      <Footer />
      <CenterWrapper>
          // button group
        <Typography variant="h3" align="center">
          User / Developer Guide <br />
          Coming Soon{" "}
        </Typography>
          {userGuideVisible && <UserGuide />}
          {devGuideVisible && <DevGuide />}
      </CenterWrapper>
    </>
  );
};

export default Guide;
