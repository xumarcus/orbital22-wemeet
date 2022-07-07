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
      const ALLOWED_TYPES = ['image/png', 'image/jpg', 'image/jpeg'];
      const selectedImage = event.target.files[0];

      if (selectedImage && ALLOWED_TYPES.includes(selectedImage.type)) {
          let reader = new FileReader();
          reader.onloadend = () => {
              setCoverImage(reader.result);
          }
          return reader.readAsDataURL(selectedImage);
      }
  }

  return (
    <Box sx={{ height: '275px', width:"100%", overflow: 'hidden' }}>
       <Box
          component="img"
          sx={{
              width: "100%"
          }}
          alt="Cover2"
          src={coverImage ? coverImage : 'cover_img.png'}
       />

       <Button variant="contained" sx={{position:"absolute", top:90, right:40}} onClick={openChooseFile}>
          Button
          <input ref={inputRef} type="file" onChange={handleChangeCover} hidden />
       </Button>
    </Box>
  )
}

export default Cover;
