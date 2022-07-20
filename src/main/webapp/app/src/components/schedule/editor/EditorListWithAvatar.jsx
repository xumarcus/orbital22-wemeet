import { UI } from '../../../core/const'
import { List, ListItem, ListItemAvatar, ListItemText } from '@mui/material'
import Avatar from '@mui/material/Avatar'
import Typography from '@mui/material/Typography'
import * as React from 'react'

const EditorListWithAvatar = ({ label, items, ...props }) => {
  const { SIZE } = UI.SCHEDULE.OWNER.EDITOR.AVATAR
  return (
    <tr>
      <td
        className='e-textlabel' style={{
          paddingTop: SIZE / 2,
          paddingBottom: SIZE / 2
        }}
      >
        {label}
      </td>
      <td colSpan={4}>
        {items.length > 0 && (
          <List dense {...props}>
            {items.map(item => (
              <ListItem>
                <ListItemAvatar>
                  <Avatar
                    sx={{
                      width: SIZE,
                      height: SIZE
                    }}
                    src='/avatar-does-not-exist'
                  />
                </ListItemAvatar>
                <ListItemText>
                  {item}
                </ListItemText>
              </ListItem>
            ))}
          </List>
        )}
        {items.length === 0 && (
          <Typography variant='body'>
            No records to display
          </Typography>
        )}
      </td>
    </tr>
  )
}

export default EditorListWithAvatar
