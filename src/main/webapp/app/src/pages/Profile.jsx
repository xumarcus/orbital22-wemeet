import * as React from 'react'
import { useContext } from 'react'
import CenterWrapper from '../components/CenterWrapper'
import AppContext from '../core/AppContext'

// TODO
const Profile = () => {
  const { context } = useContext(AppContext)

  return (
    <CenterWrapper>
      <div>
        <pre>{JSON.stringify(context.user, null, 2)}</pre>
      </div>
    </CenterWrapper>
  )
}

export default Profile
