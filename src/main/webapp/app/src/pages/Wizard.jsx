const Wizard = () => {}

  /*
  const [rosterBtnLoading, setRosterBtnLoading] = useState(false)

  const handleClick = () => {
    setRosterBtnLoading(true)
  }

  return (
    <>
      <Grid
        container
        direction='column'
        justifyContent='flex-start'
        alignItems='center'
        border='1px dashed'
        sx={{
          bgcolor: '#E3FFFC',
          boxShadow: '4',
          mt: '20px',
          width: '90%',
          ml: '5%',
          alignSelf: 'center',
          pt: 5
        }}
      >
        <Grid item>
          <Typography
            variant='h4'
            component='div'
            sx={{ border: '1px dashed', bm: 2 }}
          >
            I would like to:
          </Typography>
        </Grid>
        <Grid item sx={{ border: '1px dashed', my: 5, width: '85%' }}>
          <Stack spacing={3} sx={{ border: '5px dashed' }}>
            <LoadingButton
              variant='contained'
              size='large'
              loading={rosterBtnLoading}
              component={Link}
              to='/roster'
            >
              Generate a Roster
            </LoadingButton>
            <Button
              variant='contained' size='large' component={Link}
              to='/meeting'
            >
              Schedule a One-on-One Meetup{' '}
            </Button>
            <Typography variant='body1' align='center' fontWeight='bold'>
              Upcoming Features
            </Typography>

            <Button variant='contained' size='large' disabled>
              Schedule a Group Meetup
            </Button>
          </Stack>
        </Grid>
      </Grid>
    </>
  )
}
*/

export default Wizard