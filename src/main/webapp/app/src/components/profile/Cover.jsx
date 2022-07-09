import { useState, useRef } from 'react'
import Box from '@mui/material/Box'
import Button from '@mui/material/Button'
import * as React from 'react'

const Cover = () => {
  const [coverImage, setCoverImage] = useState(null)
  const inputRef = useRef(null)

  const openChooseFile = () => {
    inputRef.current.click()
  }

  const handleChangeCover = event => {
    const ALLOWED_TYPES = ['image/png', 'image/jpg', 'image/jpeg']
    const selectedImage = event.target.files[0]

    if (selectedImage && ALLOWED_TYPES.includes(selectedImage.type)) {
      const reader = new FileReader()
      reader.onloadend = () => {
        setCoverImage(reader.result)
      }
      return reader.readAsDataURL(selectedImage)
    }
  }

  return (
    <Box sx={{ height: '275px', width: '100%', overflow: 'hidden', position: 'relative' }}>
      <Box
        component='img'
        sx={{
          width: '100%'
        }}
        alt='Cover Image'
        src={coverImage || './images/profile_page_background.jpeg'}
      />

      <Button
        variant='contained'
        onClick={openChooseFile}
        sx={{ position: 'absolute', top: 25, right: 30, backgroundColor: 'rgba(128, 128, 128, 0.5)' }}
      >
        Change Cover
        <input ref={inputRef} type='file' onChange={handleChangeCover} hidden />
      </Button>
    </Box>
  )
}

export default Cover
