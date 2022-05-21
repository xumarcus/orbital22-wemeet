import { Grid } from "@mui/material";

const CenterWrapper = ({ children }) => (
    <Grid
        container
        direction={"column"}
        alignItems={"center"}
        justifyContent={"center"}
        style={{ minHeight: '80vh' }}>
        <Grid item>{children}</Grid>
    </Grid>
);

export default CenterWrapper;