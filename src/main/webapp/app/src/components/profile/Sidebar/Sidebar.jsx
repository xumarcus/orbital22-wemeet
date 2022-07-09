import SidebarProfile from './SidebarProfile'
import SidebarData from './SidebarData'
import Box from '@mui/material/Box'

const Sidebar = () => {
  return (
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'column',
        // justifyContent: 'space-between',
        pt: 1,
        backgroundColor: 'white',
        border: 1,
        borderRadius: 1,
        borderColor: 'grey.500',
        width: '20%',
        transform: 'translateY(-100px)'
      }}
    >
      <SidebarProfile />
      <SidebarData />
    </Box>
  )
}

export default Sidebar
