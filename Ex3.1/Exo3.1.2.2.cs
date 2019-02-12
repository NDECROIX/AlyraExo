contract DesisionsCollectives is Assemblee{

  address[] participants;

  string[] public descriptionDecisions;
  uint[] public votesPour;
  uint[] public votesContre;

  function participer() public {
    if (estMembre(msg.sender)) participants.push(msg.sender); 
  }

  function estParticipant(address utilisateur) public view returns (bool){
    for(uint i = 0; i < participants.length; i++){
      if (participants[i] == utilisateur) return true;
    }
  }

  function proposerDecision(string description) public {
    if (estParticipant(msg.sender)){
      descriptionDecisions.push(description);
      votesPour.push(0);
      votesContre.push(0);
    }
  }

  function voter(uint proposition, uint pourContre) public {
    if (pourContre == 1) votesPour[proposition]++;
    else votesContre[proposition]++;

  }
	// 3.1.2.3
  function comptabiliser(uint indice) public view returns (int){
    return int(votesPour[indice] - votesContre[indice]);
  } 

}