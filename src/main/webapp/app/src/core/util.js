export const fromTemplate = (template) => {
  const [url] = template.match(/(?:(?!\{).)+/)
  const params = [...template.matchAll(/[?,](\w+)/g)].map(([_, match]) => match)
  return { url, params }
}
