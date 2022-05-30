import React, { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import Alert from '@mui/material/Alert';

const SuccessAlert =() => {
    const navigate = useNavigate()

    useEffect(() => {
        setTimeout(() => {
            navigate('/')
        }, 2000)
    }, [])
    return <Alert severity="success">Success: You will be redirected soon.</Alert>
}

export default SuccessAlert