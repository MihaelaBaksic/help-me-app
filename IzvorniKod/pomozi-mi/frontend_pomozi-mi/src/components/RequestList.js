import React from "react"
import { List, Button, Label } from 'semantic-ui-react'
import { useEffect, useState } from 'react'

function RequestList() {
  const [requests, setRequests] = React.useState([]);
  
  /*React.useEffect(() => {
    fetch('/requests')
      .then(data => data.json)
      .then(requests => setRequests(requests)) 
  }, []);*/
  
  return(<List selection celled id="requestList">
    {/*{requests.map((request) => ( 
    <List.Item>
      <List.Header>Dođi mi na party <small>Baja Bajić</small></List.Header>
      Tamo bude sviral Cetinski i bit će brutala buraz moraš doć e --- Na krovu konzuma
      <Button positive floated='right'>Javi se</Button>
    </List.Item>  
    ))};*/}



    <List.Item>
      <List.Header>Dođi mi na party  &nbsp; <Label as='a' image>
      <img src='https://react.semantic-ui.com/images/avatar/small/joe.jpg' />
      Baja Bajić
    </Label>
    </List.Header>
      Tamo bude sviral Cetinski i bit će brutala buraz moraš doć e&nbsp;
        <Label as='a' tag>
        Na krovu konzuma
        <Label.Detail>8</Label.Detail>
      </Label>
      <Button positive floated='right'>Javi se</Button>
    </List.Item>  
    
    
    
    <List.Item>
      <List.Header>Gledaj me na Farmi &nbsp;     
        <Label as='a' image>
          <img src='https://react.semantic-ui.com/images/avatar/small/elliot.jpg' />
        Maja Bajamić
        </Label>
    </List.Header>
      Opet se guram na televiziji pomogni mi da budem popularna&nbsp;
      <Label as='a' tag>
        Babin dol
        <Label.Detail>69</Label.Detail>
      </Label>
      <Button positive floated='right'>Javi se</Button>
    </List.Item>
    
    <List.Item>
      <List.Header>Gledaj me dok se krunim &nbsp;<Label as='a' image>
      <img src='https://react.semantic-ui.com/images/avatar/small/stevie.jpg' />
      Tomislav Kralj
    </Label></List.Header>
      Krunim se kaj hoćeš da ti još kažem...&nbsp; 
      <Label  as='a' tag>
        Trg kralja Tomislava
        <Label.Detail>1</Label.Detail>
      </Label>
      <Button positive floated='right'>Javi se</Button>
    </List.Item>
    <List.Item>
      <List.Header>Pomogni mi skupit birače &nbsp;
        <Label as='a' image>
         <img src='https://react.semantic-ui.com/images/avatar/small/stevie.jpg' />
         Ivan Pernar
        </Label></List.Header>
      Skupimo se svi koji ćete glasat za mene i umnožimo se&nbsp;
      <Label  as='a' tag>
        Trg bana Jelačića
        <Label.Detail></Label.Detail>
      </Label>
      <Button positive floated='right'>Javi se</Button>
    </List.Item>
  </List>
  );
}

export default RequestList;