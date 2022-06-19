import Typography from '@mui/material/Typography';
import * as React from 'react';
import CenterWrapper from '../components/CenterWrapper';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';

import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';

// import "@fullcalendar/core/main.css";
// import "@fullcalendar/daygrid/main.css";

const About = () => {
  const [startDate, setStartDate] = React.useState(null);
  const [endDate, setEndDate] = React.useState(null);

  const [frequency, setFrequency] = React.useState('');

    const handleChange = (event) => {
        setFrequency(event.target.value);
    };

    const handleSubmit = () => {
        console.log(startDate, endDate, frequency);
    };

    const events = [{ title: "today's event", date: new Date() }];


    return (
        <>
            <CenterWrapper>
                <FullCalendar
                    defaultView="dayGridMonth"
                    plugins={[dayGridPlugin]}
                    events={events}
                />
                <Typography variant="h2" component="div" textAlign="center">
                    Make a roster here
                </Typography>
                {/*  About*/}

                  <Calendar />




            </CenterWrapper>
        </>
    );
};

export default About;
