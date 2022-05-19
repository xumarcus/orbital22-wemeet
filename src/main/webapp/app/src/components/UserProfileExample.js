import useSWR from 'swr'
import ajax from "../util";

const UserProfileExample = () => {
    const { data, error } = useSWR('/api/hello', ajax('GET'))
    console.log(data);

    if (error) return <div>Who are you?</div>
    if (!data) return <div>Loading...</div>

    // render data
    return <div>{data.data}</div>
};

export default UserProfileExample;