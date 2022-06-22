export const SYNCFUSION_LICENSE_KEY = 'ORg4AjUWIQA/Gnt2VVhhQlFaclhJXGFWfVJpTGpQdk5xdV9DaVZUTWY/P1ZhSXxRdkNiUH9dcnBVRGddV00='
export const TOOLBAR = ['Add', 'Edit', 'Delete', 'Update', 'Cancel']
export const API = {
  ROSTER_PLAN: '/api/rosterPlan',
  ROSTER_PLAN_ID: (id) => `/api/rosterPlan/${id}`,
  ROSTER_PLAN_USER_INFO: '/api/rosterPlanUserInfo',
  TIME_SLOT: '/api/timeSlot',
  ME: '/api/users/me',
}
export const ROUTES = {
  MEETING_EDIT: (id) => `/meeting/${id}/edit`,
  MEETING_RANK: (id) => `/meeting/${id}/rank`,
}
export const ERROR_MESSAGES = {
  INVALID_URL: "Invalid URL",
}
