import * as React from 'react'
import { useContext } from 'react'
import CenterWrapper from '../components/core/CenterWrapper'
import AppContext from '../core/AppContext'
import FormControl from '@mui/material/FormControl'
import { FormLabel } from '@mui/material'
import Grid from '@mui/material/Grid'
import TextField from '@mui/material/TextField'
import Button from '@mui/material/Button'
import Cover from '../components/profile/Cover'
import ProfileBody from '../components/profile/ProfileBody'

const Profile = () => {
  const { context } = useContext(AppContext)

  return (
    <>
        <Cover />
        <ProfileBody />
    </>
  )
}

export default Profile
