import useSWR from 'swr'
import ajax from "../util";

const UserProfileExample = () => {
    const { data, error } = useSWR('/api/hello', ajax('GET'))
    console.log(data);

    if (error) return <div>failed to load</div>
    if (!data) return <div>loading...</div>

    // render data
    return <div>{data.data}</div>
};

export default UserProfileExample;