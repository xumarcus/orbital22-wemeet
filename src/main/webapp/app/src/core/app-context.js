import * as React from 'react'

export const defaultAppContextValues = {
  user: null
}

const AppContext = React.createContext({
  values: defaultAppContextValues,
  setValues: () => {
  }
})
export default AppContext
