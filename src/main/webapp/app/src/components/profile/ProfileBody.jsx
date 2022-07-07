import Content from "./Content/Content.jsx"
import Sidebar from "./Sidebar/Sidebar.jsx"
import Box from "@mui/material/Box";

const ProfileBody = () => {
    return (
        <Box  display="flex" justifyContent = "space-around">
            <Sidebar />
            <Content />
        </Box>
  )
}

export default ProfileBody;