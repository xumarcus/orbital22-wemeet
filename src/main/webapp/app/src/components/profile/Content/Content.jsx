import AccountSettings from './AccountSettings'
import Notifications from './Notifications'
import Box from '@mui/material/Box'
import { TabContext, TabList, TabPanel } from '@mui/lab'
import Tab from '@mui/material/Tab'
import * as React from 'react'
import Actions from './Actions'

const Content = () => {
  const [currTab, setCurrTab] = React.useState('1')

  const handleTabChange = (event, newValue) => {
    setCurrTab(newValue)
  }

  return (
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'space-between',
        pt: 5,
        backgroundColor: 'white',
        border: 1,
        borderRadius: 1,
        borderColor: 'grey.500',
        transform: 'translateY(-100px)',
        height: '100%',
        width: '70%'
      }}
    >
      <TabContext value={currTab} sx={{ height: '100%' }}>
        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
          <TabList onChange={handleTabChange} aria-label='menu tabs'>
            <Tab label='Account Settings' value='1' sx={{ mx: 3, px: 0, py: 3 }} />
            <Tab label='Notifications' value='2' sx={{ mx: 3, px: 0, py: 3 }} />
          </TabList>
        </Box>
        <TabPanel sx={{ height: '100%' }} value='1'>
          <AccountSettings />
        </TabPanel>
        <TabPanel value='2'>
          <Notifications />
        </TabPanel>
      </TabContext>
      <Actions />
    </Box>
  )
}

export default Content
