export const setupForSyncfusionTest = () => {
  const { getComputedStyle } = global.window
  window.crypto = { getRandomValues: jest.fn() }
  window.getComputedStyle = (elem, select) => getComputedStyle(elem, select)
}

export const sleep = (ms) => new Promise(resolve => setTimeout(resolve, ms))
