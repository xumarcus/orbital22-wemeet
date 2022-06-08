import Typography from "@mui/material/Typography";
import * as React from "react";
import Header from "../components/NavBar";
import Footer from "../components/Footer";
import CenterWrapper from "../components/CenterWrapper";
import ScheduleSelector from 'react-schedule-selector'
import Box from "@mui/material/Box";

const Meetings = () => {

    const [state, setState] = React.useState({ schedule : [] });


    const handleChange = newSchedule => {
        setState({ schedule: newSchedule });
    }

    return (
        <>
            <Header/>
            <Footer/>

            <CenterWrapper>
                <Typography variant="h3" component="div" textAlign="center">
                    Meetings
                </Typography>
                <Box>
                    {/*input for Start Date*/}
                    {/*input for intervals*/}
                </Box>
                <ScheduleSelector
                    selection={state}
                    numDays={5}
                    minTime={8}
                    maxTime={22}
                    hourlyChunks={2}
                    selectionScheme = {'linear'}
                    onChange={handleChange}
                />

            </CenterWrapper>
        </>
    );
};

export default Meetings;