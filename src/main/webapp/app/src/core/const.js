import { createTheme } from '@mui/material'

export const SYNCFUSION_LICENSE_KEY = 'ORg4AjUWIQA/Gnt2VVhhQlFaclhJXGFWfVJpTGpQdk5xdV9DaVZUTWY/P1ZhSXxRdkNiUH9dcnBVRGddV00='
export const TOOLBAR = ['Add', 'Edit', 'Delete', 'Update', 'Cancel']

export const API = {
  ME: '/api/users/me',
  ROSTER_PLAN: '/api/rosterPlan',
  ROSTER_PLAN_ID: (id) => `/api/rosterPlan/${id}`,
  ROSTER_PLAN_USER_INFO: '/api/rosterPlanUserInfo',
  ROSTER_PLAN_BY_PARENT: (parent) => `/api/rosterPlan/search/findByParent?parent=${parent}`,
  ROSTER_PLAN_BY_PARENT_IS_NULL_AND_OWNER: (owner) => `/api/rosterPlan/search/findByParentIsNullAndOwner?owner=${owner}`,
  TIME_SLOT: '/api/timeSlot',
  TIME_SLOT_ID: (id) => `/api/timeSlot/${id}`,
  TIME_SLOT_USER_INFO: '/api/timeSlotUserInfo',

  PROJECTIONS: {
    ROSTER_PLAN_USER_INFO: 'rosterPlanUserInfoProjection',
    TIME_SLOT: 'timeSlotProjection'
  }
}

export const ROUTES = {
  MEETING_EDIT: (id) => `/meeting/${id}/edit`,
  MEETING_RANK: (id) => `/meeting/${id}/rank`,
  MEETING_VIEW_SOLUTION: (id) => `/meeting/${id}/view-solution`
}

// i18n support?

export const ERROR_MESSAGES = {
  DEFAULT_MESSAGE: 'An error has occurred.',
  INVALID_URL: 'Invalid URL',
  PLEASE_SIGN_IN: 'Please sign in',
  MEETING_NOT_FOUND: 'Meeting not found'
}

export const TEXT = {
  YOURSELF: 'Yourself',
  FOOTER: 'An Orbital 2022 Project',
  LOCK_STATUS: {
    LOCKED: '🔐',
    NOT_LOCKED: '🔓'
  }
}

export const ENUMS = {
  QUICK_MENU_BULK_ADD: {
    FREQUENCY: {
      DAILY: 'daily',
      WEEKDAYS: 'weekdays',
      WEEKENDS: 'weekends'
    }
  },
  NAVBAR: {
    PAGES: {},
    SETTINGS: {}
  }
}

export const SYSTEM_THEME = createTheme()
