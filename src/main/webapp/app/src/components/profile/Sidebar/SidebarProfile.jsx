import { useState, useRef } from 'react'
import Box from '@mui/material/Box'
import * as React from 'react'
import Avatar from '@mui/material/Avatar'
import Stack from '@mui/material/Stack'
import Typography from '@mui/material/Typography'
import AppContext from '../../../core/AppContext'
import {useContext} from "react";

const SidebarProfile = () => {
  const { context } = useContext(AppContext)
  const [userProfile, setUserProfile] = useState(null)
  const profileImage = useRef(null)

  const openChooseImage = () => {
    profileImage.current.click()
  }

  const handleChangeImage = event => {
    const ALLOWED_TYPES = ['image/png', 'image/jpg', 'image/jpeg']
    const selectedImage = event.target.files[0]

    if (selectedImage && ALLOWED_TYPES.includes(selectedImage.type)) {
      const reader = new FileReader()
      reader.onloadend = () => {
        setUserProfile(reader.result)
      }
      return reader.readAsDataURL(selectedImage)
    }
  }

  return (
    <Stack
      sx={{
        py: 1,
        borderBottom: 1,
        borderColor: 'grey.500',
        alignItems: 'center',
        justifyItems: 'center'
      }}
    >
      <Box>
        <Avatar
          curser='pointer'
          onClick={openChooseImage}
          alt='Profile Photo'
          src={userProfile || 'cover_img.png'}
          sx={{ width: 160, height: 160, mb: 2 }}
        />
        <input ref={profileImage} type='file' onChange={handleChangeImage} hidden />
      </Box>
      <Typography variant='h6'> User's Name </Typography>
    </Stack>

  )
}

export default SidebarProfile
