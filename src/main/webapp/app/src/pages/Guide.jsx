import Typography from '@mui/material/Typography'
import * as React from 'react'
import CenterWrapper from '../components/core/CenterWrapper'
import Link from '@mui/material/Link'

const Guide = () => (
  <CenterWrapper>
    {/* button group */}
    <Typography variant='h3' align='center'>
      User Guide Coming Soon
    </Typography>

    <Link variant='h3' align='center' href='/static/docs/index.html'>
      Developer Guide
    </Link>
  </CenterWrapper>
)

export default Guide
