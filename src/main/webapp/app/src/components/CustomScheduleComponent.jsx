import {Agenda, Day,DragAndDrop, Inject,Month,Resize,ScheduleComponent,Week } from '@syncfusion/ej2-react-schedule'
import * as React from 'react'
import {CustomDataAdaptor, DataManager } from '@syncfusion/ej2-data'
import RestAdaptor from '../core/RestAdaptor'

const CustomScheduleComponent = ({ rosterPlan }) => {
  const getUrl = rosterPlan?._links?.timeSlots?.href;

  if (getUrl === null) {
    throw new Error('Meeting not found.')
  }

  const dataManager = new DataManager({
    adaptor: new CustomDataAdaptor({
      getData: function(option) {
          let xhttp = new XMLHttpRequest();
          xhttp.onreadystatechange = function () {
            if (this.readyState === 4) {
                 let request = {...option, httpRequest: xhttp };
                 if ((xhttp.status >= 200 && xhttp.status <= 299) || xhttp.status === 304) {
                 let data = JSON.parse(xhttp.responseText);
                 option.onSuccess([{
                  Id: '1',
                  Subject: 'Hi',
                  StartTime: '2022-06-21T01:00:00.000Z',
                  EndTime: '2022-06-21T01:30:00.000Z'
                 }], request);
                } else {
                 option.onFailure(request);
                }
            }
          };
          xhttp.open('GET', '/api/timeSlot/1', true);
          xhttp.send();
      }
    })
  })

  return (
    <ScheduleComponent height='80vh' eventSettings={{ dataSource: dataManager }}>
      <Inject
        services={[Day, Week, Month, Agenda, Resize, DragAndDrop]}
      />
    </ScheduleComponent>
  )
}

export default CustomScheduleComponent