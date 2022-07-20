import { SYSTEM_THEME, TEXT } from '../../core/const'

export const getSelfInfoColor = (self, timeSlotUserInfos) => {
  const info = timeSlotUserInfos.find(({ available, user: { id } }) => available && id === self.id)

  if (!info) return SYSTEM_THEME.palette.primary.main
  if (info.picked) return SYSTEM_THEME.palette.success.main
  switch (info.rank) {
    case 0:
      return SYSTEM_THEME.palette.primary.main // Same as info === null
    case 1:
      return SYSTEM_THEME.palette.warning.light
    case 2:
      return SYSTEM_THEME.palette.warning.main
    case 3:
      return SYSTEM_THEME.palette.warning.dark
    default:
      return SYSTEM_THEME.palette.error.main
  }
}

export const getInfoSummary = (timeSlotUserInfos) => {
  const infos = timeSlotUserInfos
    .filter(({ available, rank }) => available && rank)

  const [first] = infos
  switch (infos.length) {
    case 0:
      return TEXT.INFO_SUMMARY.NONE
    case 1:
      return TEXT.INFO_SUMMARY.ONE(first.user.email)
    default:
      return TEXT.INFO_SUMMARY.MORE(first.user.email, infos.length - 1)
  }
}

export const makeScheduleEventFooter = (timeSlotUserInfos) => {
  const div = document.createElement('div')
  div.style.fontSize = '10px'
  div.style.textAlign = 'right'
  div.innerText = getInfoSummary(timeSlotUserInfos)
  return div
}
