import * as React from 'react'
import {useContext, useEffect} from 'react'
import AppContext from '../core/AppContext'
import Cover from '../components/profile/Cover'
import ProfileBody from '../components/profile/ProfileBody'
import Typography from "@mui/material/Typography";
import {useNavigate} from "react-router-dom";

const Profile = () => {
    const { context } = useContext(AppContext)
    const navigate = useNavigate();

    useEffect(() => {
        if(!context.user.id) {
            navigate("");
        }
    }, []);


  return (
    <>
        <Cover />
        <ProfileBody />
        <Typography variant='body'>
            {JSON.stringify(context.user)}
        </Typography>
    </>
  )
}

export default Profile
