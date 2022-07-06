import Typography from '@mui/material/Typography'
import * as React from 'react'
import { useContext } from 'react'
import Tabs from '@mui/material/Tabs'
import Tab from '@mui/material/Tab'
import Box from '@mui/material/Box'
import PageTitle from '../components/core/PageTitle'
import EventGrid from '../components/dashboard/EventGrid'
import AppContext from '../core/AppContext'
import ErrorFallback from '../components/core/ErrorFallback'
import { ErrorBoundary } from 'react-error-boundary'
import PendingResponseGrid from '../components/dashboard/PendingResponseGrid'

const TabPanel = ({ children, value, index, ...other }) => (
  <div
    role='tabpanel'
    hidden={value !== index}
    id={`simple-tabpanel-${index}`}
    aria-labelledby={`simple-tab-${index}`}
    {...other}
  >
    {value === index && (
      <Box sx={{ p: 3 }}>
        <Typography>{children}</Typography>
      </Box>
    )}
  </div>
)

const a11yProps = (index) => (
  {
    id: `simple-tab-${index}`,
    key: index,
    'aria-controls': `simple-tabpanel-${index}`
  }
)

const TABS = [
  'Events',
  'Pending Response'
]

const Dashboard = () => {
  const { context } = useContext(AppContext)
  const [index, setIndex] = React.useState(0)

  return (
    <>
      <PageTitle pageTitle='Dashboard' />
      <Box sx={{
        width: '100%',
        alignContent: 'center',
        justifyContent: 'center'
      }}
      >
        <Tabs
          value={index} onChange={(event, newIndex) => setIndex(newIndex)}
        >
          {TABS.map((label, index) => <Tab label={label} {...a11yProps(index)} />)}
        </Tabs>

        <Box sx={{ backgroundColor: '#efefef', height: '100vh' }}>
          <TabPanel value={index} index={0}>
            <ErrorBoundary FallbackComponent={ErrorFallback} resetKeys={[context]}>
              <EventGrid />
            </ErrorBoundary>
          </TabPanel>
          <TabPanel value={index} index={1}>
            <ErrorBoundary FallbackComponent={ErrorFallback} resetKeys={[context]}>
              <PendingResponseGrid />
            </ErrorBoundary>
          </TabPanel>
        </Box>
      </Box>
    </>
  )
}

export default Dashboard
