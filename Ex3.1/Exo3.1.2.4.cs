contract DesisionsCollectives is Assemblee{

  struct Decision {
    string descriptionDecision;
    uint votesPour;
    uint votesContre;
    mapping (address => bool) aVote;
  }

  Decision[] decisions;
  address[] participants;

  function participer() public {
    require(estMembre(msg.sender), "Il faut être membre de l'assemblée!");
    participants.push(msg.sender); 
  }

  function estParticipant(address utilisateur) public view returns (bool){
    for(uint i = 0; i < participants.length; i++){
      if (participants[i] == utilisateur) return true;
    }
  }

  function proposerDecision(string description) public {
    require(estParticipant(msg.sender), "Il faut être participant!"); 
    Decision memory decision;
    decision.descriptionDecision = description;
    decision.votesPour = 0;
    decision.votesContre = 0;
    decisions.push(decision);    
  }

  function voter(uint proposition, uint pourContre) public {
    require(decisions[proposition].aVote[msg.sender] == false);
    if (pourContre == 1) decisions[proposition].votesPour++;
    else decisions[proposition].votesContre++;
    decisions[proposition].aVote[msg.sender] = true;

  }

  function comptabiliser(uint indice) public view returns (int){
    return int(decisions[indice].votesPour - decisions[indice].votesContre);
  } 

}
