import Typography from '@mui/material/Typography';
import * as React from 'react';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';

function TabPanel(props) {
  const {children, value, index, ...other} = props;

  return (
      <div
          role="tabpanel"
          hidden={value !== index}
          id={`simple-tabpanel-${index}`}
          aria-labelledby={`simple-tab-${index}`}
          {...other}
      >
        {value === index && (
            <Box sx={{p: 3}}>
              <Typography>{children}</Typography>
            </Box>
        )}
      </div>
  );
}

function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

export default function Dashboard() {
  const [value, setValue] = React.useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
      <>
        <Typography variant="h3" component="div" textAlign="center"
                    sx={{my: 5}}>
          Dashboard
        </Typography>
        <Box sx={{my: 5}}>
          <Typography variant="body" sx={{mx: 5}}>
            Photo
          </Typography>
          <Typography variant="body" sx={{mx: 5}}>
            Name
          </Typography>
          <Typography variant="body" sx={{mx: 5}}>
            Email
          </Typography>
        </Box>
        <Box sx={{
          width: '100%',
          alignContent: 'center',
          justifyContent: 'center',
        }}>
          <Box sx={{
            borderBottom: 1,
            borderColor: 'divider',
            alignContent: 'center',
            justifyContent: 'center',
          }}>
            <Tabs value={value} onChange={handleChange}
                  aria-label="basic tabs example">
              <Tab label="My Events" {...a11yProps(0)} />
              <Tab label="Events Pending Response" {...a11yProps(1)} />
              <Tab label="(untitled)" {...a11yProps(2)} />
            </Tabs>
          </Box>
          <Box sx={{backgroundColor: '#efefef', height: '100vh'}}>
            <TabPanel value={value} index={0}>
              My Events
            </TabPanel>
            <TabPanel value={value} index={1}>
              Events Pending Response
            </TabPanel>
            <TabPanel value={value} index={2}>
              (untitled)
            </TabPanel>
          </Box>
        </Box>
      </>
  );
}