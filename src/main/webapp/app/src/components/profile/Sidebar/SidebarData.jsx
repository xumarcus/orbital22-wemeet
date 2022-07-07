import Stack from "@mui/material/Stack";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";

const SidebarData = () => {
    return (
    <Stack>
        <Box
            key="1"
            width = "100%"
            py={1}
            px={2}
            display="flex"
            alignItems="center"
            justifyContent="space-between"
            borderBottom="1"
            borderColor="grey.500"
        >
            <Typography variant="body">Total Events</Typography>
            <Typography variant="body">99</Typography>
        </Box>
        <Box
            key="2"
            width = "100%"
            py={1}
            px={2}
            display="flex"
            alignItems="center"
            justifyContent="space-between"
            borderBottom="1"
            borderColor="grey.500"
        >
            <Typography variant="body">Pending Responses</Typography>
            <Typography variant="body">50</Typography>
        </Box>
    </Stack>
    )

}

export default SidebarData;