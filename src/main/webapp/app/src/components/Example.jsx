import useSWR from 'swr'
import ajax from "../util";
import CenterWrapper from "./CenterWrapper";
import Typography from "@mui/material/Typography";

const HelloFetcher = () => {
    const { data, error } = useSWR('/api/hello', ajax('GET'))
    console.log(data);

    if (error) return <div>Who are you?</div>
    if (!data) return <div>Loading...</div>

    // render data
    return <div>{data.data}</div>
};

const Example = () => (
    <CenterWrapper>
        <Typography variant="h1" component="div">
            <HelloFetcher />
        </Typography>
    </CenterWrapper>
)

export default Example;