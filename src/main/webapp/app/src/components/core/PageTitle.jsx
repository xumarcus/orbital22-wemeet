import Typography from '@mui/material/Typography'

const PageTitle = ({ pageTitle }) => (
  <Typography
    variant='h3' component='div' textAlign='center'
    sx={{ my: 5 }}
  >
    {pageTitle}
  </Typography>
)

export default PageTitle
