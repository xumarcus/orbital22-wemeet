import { createTheme } from '@mui/material'

export const SYNCFUSION_LICENSE_KEY = 'ORg4AjUWIQA/Gnt2VVhhQlFaclhJXGFWfVJpTGpQdk5xdV9DaVZUTWY/P1ZhSXxRdkNiUH9dcnBVRGddV00='
export const TOOLBAR = ['Add', 'Edit', 'Delete', 'Update', 'Cancel']

export const API = {
  ME: '/api/users/me',
  ROSTER_PLAN: '/api/rosterPlan',
  ROSTER_PLAN_ID: (id) => `/api/rosterPlan/${id}`,
  ROSTER_PLAN_ID_WITH_PROJECTION: (id, projection) => `/api/rosterPlan/${id}?projection=${projection}`,
  ROSTER_PLAN_USER_INFO: '/api/rosterPlanUserInfo',
  ROSTER_PLAN_BY_PARENT: (parent) => `/api/rosterPlan/search/findByParent?parent=${parent}`,
  ROSTER_PLAN_BY_PARENT_IS_NULL_AND_OWNER: (owner) => `/api/rosterPlan/search/findByParentIsNullAndOwner?owner=${owner}`,
  ROSTER_PLAN_PUBLISH: '/api/rosterPlan/publish',
  TIME_SLOT: '/api/timeSlot',
  TIME_SLOT_ID: (id) => `/api/timeSlot/${id}`,
  TIME_SLOT_USER_INFO: '/api/timeSlotUserInfo',

  PROJECTIONS: {
    ROSTER_PLAN_USER_INFO: 'rosterPlanUserInfoProjection',
    TIME_SLOT: 'timeSlotProjection',
    ROSTER_PLAN: 'rosterPlanProjection'
  }
}

export const ROUTES = {
  MEETING_EDIT: (id) => `/meeting/${id}/edit`,
  MEETING_RANK: (id) => `/meeting/${id}/rank`,
  MEETING_VIEW_SOLUTION: (id) => `/meeting/${id}/view-solution`
}

export const ENUMS = {
  SCHEDULE: {
    OWNER: {
      CONFIGURER: {
        DURATIONS: {
          FIFTEEN: 15,
          THIRTY: 30,
          HOURLY: 60
        }
      }
    }
  }
}

// i18n support?

export const ERROR_MESSAGES = {
  DEFAULT_MESSAGE: 'An error has occurred',
  INVALID_URL: 'Invalid URL',
  PLEASE_SIGN_IN: 'Please sign in',
  MEETING_NOT_FOUND: 'Meeting not found'
}

export const TEXT = {
  YOURSELF: 'Yourself',
  FOOTER: 'An Orbital 2022 Project',
  INFO_SUMMARY: {
    NONE: 'Nobody\nis picking this',
    ONE: (email) => `${email}\nis picking this`,
    MORE: (email, remainingCount) => `${email}\nand ${remainingCount} more...`
  },
  SCHEDULE: {
    OWNER: {
      CONFIGURER: {
        DURATIONS: [
          { value: 15, label: '15 minutes' },
          { value: 30, label: '30 minutes' },
          { value: 60, label: '1 hour' }
        ],
        EVENT_DURATION: 'Default event duration',
        NUMBER_OF_SLOTS: 'Number of slots per user',
      }
    }
  },
  MEETING: {
    VIEW_SOLUTION: {
      PUBLISHED_SUCCESSFULLY: 'Published successfully'
    }
  }
}

export const UI = {
  SCHEDULE: {
    OWNER: {
      EDITOR: {
        AVATAR: {
          SIZE: 32
        }
      }
    }
  }
}

export const CONFIG = {
    SCHEDULE: {
      OWNER: {
        CONFIGURER: {
          NUMBER_OF_SLOTS: {
            MIN: 1,
            MAX: 10,
          }
        }
      }
    }
}

export const SYSTEM_THEME = createTheme()
