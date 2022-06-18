import Typography from "@mui/material/Typography";
import * as React from "react";
import { useState } from "react";
import Header from "../components/NavBar";
import Footer from "../components/Footer";
import ScheduleSelector from 'react-schedule-selector'
import Box from "@mui/material/Box";
import ButtonGroup from '@mui/material/ButtonGroup';
import Button from '@mui/material/Button';
import InputLabel from "@mui/material/InputLabel";
import FormControl from "@mui/material/FormControl";
import {Select} from "@mui/material";
import MenuItem from "@mui/material/MenuItem";
import {DesktopDatePicker, LocalizationProvider} from "@mui/lab";
import TextField from "@mui/material/TextField";
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import enLocale from 'date-fns/locale/en-GB';
import ArrowCircleLeftIcon from '@mui/icons-material/ArrowCircleLeft';
import ArrowCircleRightIcon from '@mui/icons-material/ArrowCircleRight';
import addDays from 'date-fns/addDays';

const Meetings = () => {
    const [choice1, setChoice1] = useState([]);
    const [choice2, setChoice2] = useState([]);
    const [choice3, setChoice3] = useState([]);
    const [currChoice, setCurrChoice] = useState("1");
    const [startDate, setStartDate] = useState(new Date());

    const [interval, setInterval] = useState(2);

    const handleChange = newSchedule => {
        ((currChoice === "1" && setChoice1(newSchedule)) ||
            (currChoice === "2" && setChoice2(newSchedule)) ||
            (currChoice === "3" && setChoice3(newSchedule)))
    }

    const handleSwitchChoice = (event, source) => {
        setCurrChoice(source);
    }

    const handleIntervalChange = event => {
        setInterval(event.target.value);
    }

    const handleStartDateChange = newStartDate => {
        setStartDate(newStartDate);
    }

    const handleShiftDatesEarlier = () => {
        setStartDate(addDays(startDate, -5))
    }

    const handleShiftDatesLater = () => {
        setStartDate(addDays(startDate, 5))
    }

    const handleClearAll = () => {
        setChoice1([]);
        setChoice2([]);
        setChoice3([]);
    }

    const handleSubmit = () => {
        // api call
        handleClearAll();
    }

    return (
        <>
            <Header/>
            <Footer/>
            <Typography variant="h3" component="div" textAlign="center">
                Meetings
            </Typography>
            <Box
            sx={{my: 2,
                mx: 5,
                alignItems: "center",
                justifyContent: "center",
                }}
            >
                <Box
                    sx={{
                        display: "flex",
                        m:5,
                        justifyContent: "space-evenly"
                    }}
                >
                    <FormControl sx={{minWidth: 120 }}>
                        <InputLabel id="interval">Time Intervals</InputLabel>
                        <Select
                            labelId="interval"
                            id="interval"
                            value={interval}
                            label="Intervals"
                            onChange={handleIntervalChange}
                        >
                            <MenuItem value={12}>5 Min</MenuItem>
                            <MenuItem value={4}>15 Min</MenuItem>
                            <MenuItem value={2}>30 Min</MenuItem>
                            <MenuItem value={1}>1 Hour</MenuItem>
                        </Select>
                    </FormControl>
                    <LocalizationProvider
                        dateAdapter={AdapterDateFns}
                        adapterLocale={enLocale}>
                        <DesktopDatePicker
                            sx={{ mx: 5 }}
                            label="Start Date"
                            inputFormat="dd/MM/yyyy"
                            value={startDate}
                            onChange={handleStartDateChange}
                            renderInput={(params) => <TextField {...params} />}
                        />
                    </LocalizationProvider>
                </Box>
                <Box sx={{width: "100%",
                    justifyContent:"center"}}>
                    <ArrowCircleLeftIcon onClick={handleShiftDatesEarlier} />
                    <ButtonGroup
                        variant="contained"
                        aria-label="outlined primary button group"
                        sx={{justifySelf:"center"}}>
                        <Button onClick={(event) => handleSwitchChoice(event, "1")}>1st Choice</Button>
                        <Button onClick={(event) => handleSwitchChoice(event, "2")}>2nd Choice</Button>
                        <Button onClick={(event) => handleSwitchChoice(event, "3")}>3rd Choice</Button>
                    </ButtonGroup>
                    <ArrowCircleRightIcon onClick={handleShiftDatesLater}/>
                </Box>
            </Box>
            <Box
                sx = {{ mx: 5, my: 5}}
                >
            <ScheduleSelector
                startDate = {startDate}
                selection={(currChoice === "1" && choice1) ||
                    (currChoice === "2" && choice2) ||
                    (currChoice === "3" && choice3)}
                dateFormat = {"D MMM"}
                timeFormat = {"h.mma"}
                numDays={5}
                minTime={8}
                maxTime={22}
                hourlyChunks={interval}
                selectionScheme = {'linear'}
                onChange={handleChange}
            />
                <Box sx={{
                    my: 5,
                    mb: 10,
                    px:2
                }}>
                    <Button variant="outlined" onClick={handleClearAll}>
                        Clear All
                    </Button>
                    <Button variant="contained" color="success" onClick={handleSubmit}>
                        Submit
                    </Button>
                </Box>
            </Box>
            {/*</CenterWrapper>*/}
        </>
    );
};

export default Meetings;