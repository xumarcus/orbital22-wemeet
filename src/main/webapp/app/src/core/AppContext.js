import * as React from 'react'

export const defaultAppContext = {
  user: {
    id: null,
    authorities: [],
    email: null,
    enabled: null,
    registered: null,
    _links: {
      ownedRosterPlans: {
        href: null
      },
      rosterPlanUserInfos: {
        href: null
      },
      self: {
        href: null
      },
      timeSlotUserInfos: {
        href: null
      },
      user: {
        href: null
      }
    }
  }
}

const AppContext = React.createContext({
  context: defaultAppContext,
  setContext: () => {}
})
export default AppContext
