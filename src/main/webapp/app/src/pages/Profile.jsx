import Typography from '@mui/material/Typography';
import * as React from 'react';
import {useContext} from 'react';
import CenterWrapper from '../components/CenterWrapper';
import AppContext from '../core/app-context';

// TODO
const Profile = () => {
  const appContext = useContext(AppContext);

  return (
      <CenterWrapper>
        <Typography variant="h3" component="div" textAlign="center">
          {JSON.stringify(appContext.values.user)}
        </Typography>
      </CenterWrapper>
  );
};

export default Profile;