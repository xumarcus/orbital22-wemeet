import { TEXT } from '../../core/const'

const getInfoSummary = (timeSlotUserInfos) => {
  // TODO? BE considers rank = 0 as definitely not going... for now
  const infos = timeSlotUserInfos.filter(({ rank }) => rank > 0)

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

const ScheduleEventFooter = (timeSlotUserInfos) => {
  const div = document.createElement('div')
  div.style.fontSize = '10px'
  div.style.textAlign = 'right'
  div.innerText = getInfoSummary(timeSlotUserInfos)
  return div
}

export default ScheduleEventFooter
