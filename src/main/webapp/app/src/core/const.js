import { createTheme } from '@mui/material'

export const SYNCFUSION_LICENSE_KEY = 'ORg4AjUWIQA/Gnt2VVhhQlFaclhJXGFWfVJpTGpQdk5xdV9DaVZUTWY/P1ZhSXxRdkNiUH9dcnBVRGddV00='
export const TOOLBAR = ['Add', 'Edit', 'Delete', 'Update', 'Cancel']

export const API = {
  ME: '/api/users/me',
  ROSTER_PLAN: '/api/rosterPlan',
  ROSTER_PLAN_ID: (id) => `/api/rosterPlan/${id}`,
  ROSTER_PLAN_USER_INFO: '/api/rosterPlanUserInfo',
  ROSTER_PLAN_BY_PARENT: (parent) => `/api/rosterPlan/search/findByParent?parent=${parent}`,
  TIME_SLOT: '/api/timeSlot',
  TIME_SLOT_ID: (id) => `/api/timeSlot/${id}`,
  TIME_SLOT_USER_INFO: '/api/timeSlotUserInfo'
}

export const ROUTES = {
  MEETING_EDIT: (id) => `/meeting/${id}/edit`,
  MEETING_RANK: (id) => `/meeting/${id}/rank`,
  MEETING_VIEW_SOLUTION: (id) => `/meeting/${id}/view-solution`
}

// i18n support?

export const ERROR_MESSAGES = {
  INVALID_URL: 'Invalid URL',
  PLEASE_SIGN_IN: 'Please sign in'
}

export const TEXT = {
  YOURSELF: 'Yourself'
}

export const SYSTEM_THEME = createTheme()
