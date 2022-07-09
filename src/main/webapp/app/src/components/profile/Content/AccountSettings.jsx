import { FormLabel, Input, Select } from '@mui/material'
import FormControl from '@mui/material/FormControl'
import AppContext from '../../../core/AppContext'
import Box from '@mui/material/Box'
import {useContext, useState} from "react";
import TextField from "@mui/material/TextField";

const AccountSettings = () => {
    const { context } = useContext(AppContext)
    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [email, setEmail] = useState(context.user.email || '')

    return (
        <Box
            sx={{
                display: 'grid',
                gridTemplateColumns: 'repeat(2, 1fr)',
                gap: 6,
                height: '100%'
            }}
        >
            <FormControl id='firstName'>
                <FormLabel>First Name</FormLabel>
                <TextField
                    variant="standard"
                    focusBorderColor='brand.blue'
                    type='text'
                    value={firstName}
                />
            </FormControl>
            <FormControl id='lastName'>
                <FormLabel>Last Name</FormLabel>
                <TextField
                    variant="standard"
                    focusBorderColor='brand.blue'
                    type='text'
                    value={lastName}
                />
            </FormControl>
            {/*<FormControl id='phoneNumber'>*/}
            {/*  <FormLabel>Phone Number</FormLabel>*/}
            {/*  <Input*/}
            {/*    focusBorderColor='brand.blue'*/}
            {/*    type='tel'*/}
            {/*    placeholder='(408) 996–1010'*/}
            {/*  />*/}
            {/*</FormControl>*/}
            <FormControl id='emailAddress'>
                <FormLabel>Email Address</FormLabel>
                <TextField
                    variant="standard"
                    focusBorderColor='brand.blue'
                    type='email'
                    value={email}
                />
            </FormControl>
            {/*<FormControl id='city'>*/}
            {/*  <FormLabel>City</FormLabel>*/}
            {/*  <Select focusBorderColor='brand.blue' placeholder='Select city'>*/}
            {/*    <option value='california'>California</option>*/}
            {/*    <option value='washington'>Washington</option>*/}
            {/*    <option value='toronto'>Toronto</option>*/}
            {/*    <option value='newyork' selected>*/}
            {/*      New York*/}
            {/*    </option>*/}
            {/*    <option value='london'>London</option>*/}
            {/*    <option value='netherland'>Netherland</option>*/}
            {/*    <option value='poland'>Poland</option>*/}
            {/*  </Select>*/}
            {/*</FormControl>*/}
            {/*<FormControl id='country'>*/}
            {/*  <FormLabel>Country</FormLabel>*/}
            {/*  <Select focusBorderColor='brand.blue' placeholder='Select country'>*/}
            {/*    <option value='america' selected>*/}
            {/*      America*/}
            {/*    </option>*/}
            {/*    <option value='england'>England</option>*/}
            {/*    <option value='poland'>Poland</option>*/}
            {/*  </Select>*/}
            {/*</FormControl>*/}
        </Box>
    )
}

export default AccountSettings
